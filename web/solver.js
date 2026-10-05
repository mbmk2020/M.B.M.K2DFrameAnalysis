/*
 * 2D frame solver (direct stiffness method, Euler-Bernoulli beam-column elements).
 *
 * Units: lengths m, forces kN, moments kN·m, E in GPa, I in mm^4, A in mm^2.
 * Conventions: global +x right, +y up, rotation counter-clockwise positive.
 * Member local axis x runs from joint I to joint J; local y is x rotated 90° CCW.
 * Bending moment is positive when sagging (tension on the local -y face).
 * Axial force is positive in tension.
 */
(function (root, factory) {
  if (typeof module === 'object' && module.exports) module.exports = factory();
  else root.FrameSolver = factory();
})(typeof self !== 'undefined' ? self : this, function () {
  'use strict';

  /** Restrained [ux, uy, rz] for each support type. */
  const SUPPORTS = {
    free: [0, 0, 0],
    fixed: [1, 1, 1],
    pinned: [1, 1, 0],
    rollerY: [0, 1, 0],
    rollerX: [1, 0, 0],
  };

  const GAUSS = [
    [-Math.sqrt(3 / 5), 5 / 9],
    [0, 8 / 9],
    [Math.sqrt(3 / 5), 5 / 9],
  ];

  const zeros = (n) => Array.from({ length: n }, () => new Array(n).fill(0));
  const clamp = (v, lo, hi) => Math.min(Math.max(v, lo), hi);

  /** Solve A x = b by Gaussian elimination with partial pivoting. Returns null if singular. */
  function solveLinear(A, b) {
    const n = b.length;
    const M = A.map((row, i) => row.concat(b[i]));
    let scale = 0;
    for (let i = 0; i < n; i++) scale = Math.max(scale, Math.abs(A[i][i]));
    const tol = 1e-11 * (scale || 1);
    for (let k = 0; k < n; k++) {
      let p = k;
      for (let i = k + 1; i < n; i++) if (Math.abs(M[i][k]) > Math.abs(M[p][k])) p = i;
      if (Math.abs(M[p][k]) < tol) return null;
      if (p !== k) [M[p], M[k]] = [M[k], M[p]];
      const pivot = M[k][k];
      for (let i = k + 1; i < n; i++) {
        const f = M[i][k] / pivot;
        if (f === 0) continue;
        const Mi = M[i], Mk = M[k];
        for (let j = k; j <= n; j++) Mi[j] -= f * Mk[j];
      }
    }
    const x = new Array(n).fill(0);
    for (let i = n - 1; i >= 0; i--) {
      let s = M[i][n];
      for (let j = i + 1; j < n; j++) s -= M[i][j] * x[j];
      x[i] = s / M[i][i];
    }
    return x;
  }

  /** Hermite shape functions (transverse) and their slopes at xi = x / L. */
  function shape(xi, L) {
    return {
      N: [1 - 3 * xi * xi + 2 * xi ** 3, L * (xi - 2 * xi * xi + xi ** 3), 3 * xi * xi - 2 * xi ** 3, L * (-xi * xi + xi ** 3)],
      dN: [(-6 * xi + 6 * xi * xi) / L, 1 - 4 * xi + 3 * xi * xi, (6 * xi - 6 * xi * xi) / L, -2 * xi + 3 * xi * xi],
    };
  }

  /** Consistent (equivalent) nodal load vector, local axes, for one local load. */
  function equivalentLoads(load, L) {
    const f = [0, 0, 0, 0, 0, 0];
    const addPoint = (a, px, py, w) => {
      const xi = a / L;
      const { N } = shape(xi, L);
      f[0] += w * px * (1 - xi);
      f[3] += w * px * xi;
      f[1] += w * py * N[0];
      f[2] += w * py * N[1];
      f[4] += w * py * N[2];
      f[5] += w * py * N[3];
    };
    if (load.kind === 'point') {
      addPoint(load.a, load.px, load.py, 1);
    } else if (load.kind === 'uniform') {
      for (const [g, wt] of GAUSS) {
        const x = load.a + ((g + 1) / 2) * load.len;
        addPoint(x, load.qx, load.qy, (wt * load.len) / 2);
      }
    } else if (load.kind === 'moment') {
      const { dN } = shape(load.a / L, L);
      f[1] += load.C * dN[0];
      f[2] += load.C * dN[1];
      f[4] += load.C * dN[2];
      f[5] += load.C * dN[3];
    }
    return f;
  }

  function localStiffness(EA, EI, L) {
    const k = zeros(6);
    const a = EA / L, b = (12 * EI) / L ** 3, c = (6 * EI) / L ** 2, d = (4 * EI) / L, e = (2 * EI) / L;
    k[0][0] = a; k[0][3] = -a; k[3][0] = -a; k[3][3] = a;
    k[1][1] = b; k[1][2] = c; k[1][4] = -b; k[1][5] = c;
    k[2][1] = c; k[2][2] = d; k[2][4] = -c; k[2][5] = e;
    k[4][1] = -b; k[4][2] = -c; k[4][4] = b; k[4][5] = -c;
    k[5][1] = c; k[5][2] = e; k[5][4] = -c; k[5][5] = d;
    return k;
  }

  /** Static condensation of the released local DOFs (moment hinges). Mutates k and f0. */
  function condense(k, f0, released) {
    for (const r of released) {
      const krr = k[r][r];
      if (Math.abs(krr) < 1e-12) continue;
      const col = k.map((row) => row[r]);
      const row = k[r].slice();
      const fr = f0[r];
      for (let i = 0; i < 6; i++) {
        for (let j = 0; j < 6; j++) k[i][j] -= (col[i] * row[j]) / krr;
        f0[i] -= (col[i] * fr) / krr;
      }
      for (let i = 0; i < 6; i++) { k[r][i] = 0; k[i][r] = 0; }
      f0[r] = 0;
    }
  }

  function validate(model) {
    const { nodes = [], members = [] } = model;
    if (nodes.length < 2) throw new Error('Add at least two joints.');
    if (members.length < 1) throw new Error('Add at least one member.');
    members.forEach((m, n) => {
      const label = `Member ${n + 1}`;
      if (!nodes[m.i] || !nodes[m.j]) throw new Error(`${label} refers to a joint that does not exist.`);
      if (m.i === m.j) throw new Error(`${label} connects a joint to itself.`);
      if (!(m.E > 0 && m.I > 0 && m.A > 0)) throw new Error(`${label}: E, I and A must be greater than zero.`);
      if (Math.hypot(nodes[m.j].x - nodes[m.i].x, nodes[m.j].y - nodes[m.i].y) < 1e-9) throw new Error(`${label} has zero length.`);
    });
    for (const l of model.nodeLoads || []) if (!nodes[l.node]) throw new Error('A joint load refers to a joint that does not exist.');
    for (const l of model.memberLoads || []) if (!members[l.member]) throw new Error('A member load refers to a member that does not exist.');
  }

  /** Convert a user load (global gravity / local perpendicular / local axial) to local components. */
  function toLocalLoad(l, L, c, s) {
    const v = Number(l.value) || 0;
    if (l.type === 'moment') return { kind: 'moment', a: clamp(Number(l.a) || 0, 0, L), C: v };
    let qx = 0, qy = 0;
    if (l.dir === 'perp') qy = v;
    else if (l.dir === 'axial') qx = v;
    else { qx = -v * s; qy = -v * c; } // gravity: positive value acts downward
    if (l.type === 'uniform') {
      const a = clamp(Number(l.a) || 0, 0, L);
      const len = l.len === '' || l.len == null || Number.isNaN(Number(l.len)) ? L - a : clamp(Number(l.len), 0, L - a);
      return { kind: 'uniform', a, len, qx, qy };
    }
    return { kind: 'point', a: clamp(Number(l.a) || 0, 0, L), px: qx, py: qy };
  }

  /** Internal forces at local position x. side < 0 / > 0 selects the left / right of a discontinuity. */
  function sectionForces(m, x, side) {
    const f = m.endForces;
    let N = -f[0], V = f[1], M = -f[2] + x * f[1];
    for (const l of m.loads) {
      if (l.kind === 'uniform') {
        const c = clamp(x - l.a, 0, l.len);
        N -= l.qx * c; V += l.qy * c; M += l.qy * c * (x - l.a - c / 2);
      } else if (x > l.a || (side > 0 && x === l.a)) {
        if (l.kind === 'point') { N -= l.px; V += l.py; M += l.py * (x - l.a); }
        else M -= l.C;
      }
    }
    return { N, V, M };
  }

  function buildStations(m) {
    const L = m.L, pts = [];
    const n = 40;
    for (let i = 0; i <= n; i++) pts.push({ x: (L * i) / n, split: i === 0 || i === n });
    for (const l of m.loads) {
      pts.push({ x: l.a, split: true });
      if (l.kind === 'uniform') pts.push({ x: l.a + l.len, split: true });
    }
    pts.sort((p, q) => p.x - q.x);
    const out = { x: [], N: [], V: [], M: [] };
    const push = (x, side) => {
      const s = sectionForces(m, x, side);
      out.x.push(x); out.N.push(s.N); out.V.push(s.V); out.M.push(s.M);
    };
    let last = null;
    for (const p of pts) {
      if (last !== null && Math.abs(p.x - last) < 1e-12) continue;
      last = p.x;
      if (p.split) {
        if (p.x > 1e-12) push(p.x, -1);
        if (p.x < L - 1e-12) push(p.x, 1);
      } else push(p.x, 0);
    }
    return out;
  }

  /** Transverse and axial displacement along the member (local axes) by integrating M / EI. */
  function buildDeflection(m) {
    const n = 100, h = m.L / n;
    const xs = [], v = [], u = [];
    let theta = m.uLocal[2], disp = m.uLocal[1];
    for (let i = 0; i <= n; i++) {
      const x = i * h;
      xs.push(x); v.push(disp);
      u.push(m.uLocal[0] + (m.uLocal[3] - m.uLocal[0]) * (x / m.L));
      if (i < n) {
        const Mmid = sectionForces(m, x + h / 2, 0).M;
        const dTheta = (Mmid * h) / m.EI;
        disp += theta * h + (dTheta * h) / 2;
        theta += dTheta;
      }
    }
    const miss = m.uLocal[4] - v[n]; // spread the small integration error linearly
    for (let i = 0; i <= n; i++) v[i] += miss * (i / n);
    return { x: xs, v, u };
  }

  function solve(model) {
    validate(model);
    const nodes = model.nodes, N = nodes.length, ndof = 3 * N;
    const warnings = [];

    // --- members: geometry, stiffness, fixed-end forces -----------------------------------
    const members = model.members.map((def, idx) => {
      const a = nodes[def.i], b = nodes[def.j];
      const L = Math.hypot(b.x - a.x, b.y - a.y);
      const c = (b.x - a.x) / L, s = (b.y - a.y) / L;
      const EA = def.E * def.A, EI = def.E * def.I * 1e-6; // kN, kN·m²
      const loads = (model.memberLoads || []).filter((l) => l.member === idx).map((l) => toLocalLoad(l, L, c, s));
      const f0 = [0, 0, 0, 0, 0, 0];
      for (const l of loads) equivalentLoads(l, L).forEach((v, k) => (f0[k] -= v));
      const k0 = localStiffness(EA, EI, L);
      const released = [];
      if (def.relI) released.push(2);
      if (def.relJ) released.push(5);
      const k = k0.map((r) => r.slice()), f = f0.slice();
      condense(k, f, released);
      return { idx, i: def.i, j: def.j, L, c, s, EA, EI, loads, k0, f00: f0, k, f0: f, released };
    });

    // --- assemble ------------------------------------------------------------------------
    const K = zeros(ndof), F = new Array(ndof).fill(0);
    const dofsOf = (m) => [3 * m.i, 3 * m.i + 1, 3 * m.i + 2, 3 * m.j, 3 * m.j + 1, 3 * m.j + 2];
    const toGlobalMatrix = (m) => {
      const { c, s } = m;
      const T = zeros(6);
      for (const o of [0, 3]) {
        T[o][o] = c; T[o][o + 1] = s; T[o + 1][o] = -s; T[o + 1][o + 1] = c; T[o + 2][o + 2] = 1;
      }
      return T;
    };
    for (const m of members) {
      m.T = toGlobalMatrix(m);
      const dofs = dofsOf(m);
      // Kg = T' k T
      const kT = zeros(6);
      for (let i = 0; i < 6; i++) for (let j = 0; j < 6; j++) { let sum = 0; for (let p = 0; p < 6; p++) sum += m.k[i][p] * m.T[p][j]; kT[i][j] = sum; }
      for (let i = 0; i < 6; i++) for (let j = 0; j < 6; j++) { let sum = 0; for (let p = 0; p < 6; p++) sum += m.T[p][i] * kT[p][j]; K[dofs[i]][dofs[j]] += sum; }
      for (let i = 0; i < 6; i++) { let sum = 0; for (let p = 0; p < 6; p++) sum += m.T[p][i] * m.f0[p]; F[dofs[i]] -= sum; }
    }
    for (const l of model.nodeLoads || []) {
      F[3 * l.node] += Number(l.fx) || 0;
      F[3 * l.node + 1] += Number(l.fy) || 0;
      F[3 * l.node + 2] += Number(l.m) || 0;
    }

    // --- boundary conditions -------------------------------------------------------------
    let maxDiag = 0;
    for (let d = 0; d < ndof; d++) maxDiag = Math.max(maxDiag, K[d][d]);
    const restrained = new Array(ndof).fill(false);
    nodes.forEach((n, i) => {
      const r = SUPPORTS[n.support] || SUPPORTS.free;
      for (let d = 0; d < 3; d++) restrained[3 * i + d] = !!r[d];
    });
    const connected = new Set();
    members.forEach((m) => { connected.add(m.i); connected.add(m.j); });
    nodes.forEach((_, i) => { if (!connected.has(i)) warnings.push(`Joint ${i + 1} is not connected to any member.`); });
    const locked = new Array(ndof).fill(false); // DOFs with no stiffness at all (e.g. rotation at a pin-only joint)
    for (let d = 0; d < ndof; d++) {
      if (!restrained[d] && K[d][d] <= 1e-10 * maxDiag) locked[d] = true;
    }
    const free = [];
    for (let d = 0; d < ndof; d++) if (!restrained[d] && !locked[d]) free.push(d);

    const U = new Array(ndof).fill(0);
    if (free.length) {
      const Kff = free.map((r) => free.map((c) => K[r][c]));
      const x = solveLinear(Kff, free.map((d) => F[d]));
      if (!x) throw new Error('The structure is unstable (a mechanism). Add supports or members, or check the hinges.');
      free.forEach((d, k) => (U[d] = x[k]));
    }

    // --- member end forces, recovered local displacements --------------------------------
    for (const m of members) {
      const dofs = dofsOf(m);
      const ug = dofs.map((d) => U[d]);
      const ul = m.T.map((row) => row.reduce((sum, v, k) => sum + v * ug[k], 0));
      if (m.released.length) { // recover the rotation of the hinged end(s) from the original stiffness
        const R = m.released, other = [0, 1, 2, 3, 4, 5].filter((d) => !R.includes(d));
        const A = R.map((r) => R.map((q) => m.k0[r][q]));
        const rhs = R.map((r) => -(m.f00[r] + other.reduce((sum, q) => sum + m.k0[r][q] * ul[q], 0)));
        const sol = solveLinear(A, rhs);
        if (sol) R.forEach((r, k) => (ul[r] = sol[k]));
      }
      m.uLocal = ul;
      m.endForces = m.k.map((row, i) => row.reduce((sum, v, q) => sum + v * ul[q], 0) + m.f0[i]);
      m.endForces.forEach((v, i) => { if (Math.abs(v) < 1e-9) m.endForces[i] = 0; });
    }

    // --- reactions: R = sum(member end forces, global) - applied load --------------------
    const sumF = new Array(ndof).fill(0);
    for (const m of members) {
      const dofs = dofsOf(m);
      for (let i = 0; i < 6; i++) { let sum = 0; for (let p = 0; p < 6; p++) sum += m.T[p][i] * m.endForces[p]; sumF[dofs[i]] += sum; }
    }
    const applied = new Array(ndof).fill(0);
    for (const l of model.nodeLoads || []) {
      applied[3 * l.node] += Number(l.fx) || 0;
      applied[3 * l.node + 1] += Number(l.fy) || 0;
      applied[3 * l.node + 2] += Number(l.m) || 0;
    }
    const reactions = [];
    nodes.forEach((n, i) => {
      if (!(SUPPORTS[n.support] || []).some(Boolean)) return;
      const r = [0, 1, 2].map((d) => (restrained[3 * i + d] ? sumF[3 * i + d] - applied[3 * i + d] : 0));
      reactions.push({ node: i, fx: r[0], fy: r[1], m: r[2] });
    });

    // --- diagrams ------------------------------------------------------------------------
    const memberResults = members.map((m) => {
      const st = buildStations(m);
      const defl = buildDeflection(m);
      const peak = (arr) => {
        let k = 0;
        arr.forEach((v, i) => { if (Math.abs(v) > Math.abs(arr[k])) k = i; });
        return { value: arr[k], x: st.x[k] };
      };
      return {
        index: m.idx, i: m.i, j: m.j, length: m.L, c: m.c, s: m.s, endForces: m.endForces,
        stations: st, deflection: defl,
        max: { N: peak(st.N), V: peak(st.V), M: peak(st.M) },
      };
    });

    const displacements = nodes.map((_, i) => ({ ux: U[3 * i], uy: U[3 * i + 1], rz: U[3 * i + 2] }));
    return { displacements, reactions, members: memberResults, warnings };
  }

  return { solve, SUPPORTS, solveLinear };
});
