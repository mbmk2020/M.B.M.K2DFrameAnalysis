// Run with: node web/solver.test.js   (no dependencies)
const assert = require('assert');
const { solve } = require('./solver.js');

const E = 200, I = 60e6, A = 8000;
const EI = E * I * 1e-6; // kN·m²
const sec = { E, I, A };
let passed = 0;
const close = (actual, expected, label, rel = 1e-6) => {
  const tol = rel * Math.max(1, Math.abs(expected));
  assert.ok(Math.abs(actual - expected) <= tol, `${label}: got ${actual}, expected ${expected}`);
};
const test = (name, fn) => { fn(); passed++; console.log('ok  ' + name); };
const reaction = (r, n) => r.reactions.find((x) => x.node === n);
const beam = (L, sup1, sup2, extra = {}) => ({
  nodes: [{ x: 0, y: 0, support: sup1 }, { x: L, y: 0, support: sup2 }],
  members: [{ i: 0, j: 1, ...sec }],
  nodeLoads: [], memberLoads: [], ...extra,
});

test('cantilever, tip load: deflection PL³/3EI and base moment PL', () => {
  const L = 5, P = 10;
  const r = solve(beam(L, 'fixed', 'free', { nodeLoads: [{ node: 1, fx: 0, fy: -P, m: 0 }] }));
  close(r.displacements[1].uy, -(P * L ** 3) / (3 * EI), 'tip deflection');
  close(r.displacements[1].rz, -(P * L ** 2) / (2 * EI), 'tip rotation');
  close(reaction(r, 0).fy, P, 'Ry');
  close(reaction(r, 0).m, P * L, 'base moment');
});

test('simply supported, UDL: 5wL⁴/384EI, wL/2, wL²/8', () => {
  const L = 6, w = 12;
  const r = solve(beam(L, 'pinned', 'rollerY', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w, a: 0, len: '' }] }));
  close(reaction(r, 0).fy, (w * L) / 2, 'Ry left');
  close(reaction(r, 1).fy, (w * L) / 2, 'Ry right');
  close(r.members[0].max.M.value, (w * L * L) / 8, 'Mmax');
  const mid = r.members[0].deflection;
  close(mid.v[50], -(5 * w * L ** 4) / (384 * EI), 'mid-span deflection', 1e-4);
  close(r.members[0].max.V.value, (w * L) / 2, 'Vmax');
});

test('fixed-fixed, UDL: end moment wL²/12, mid moment wL²/24', () => {
  const L = 4, w = 20;
  const r = solve(beam(L, 'fixed', 'fixed', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w }] }));
  close(Math.abs(reaction(r, 0).m), (w * L * L) / 12, 'end moment');
  const st = r.members[0].stations;
  const midM = st.M[st.x.findIndex((x) => Math.abs(x - L / 2) < 1e-9)];
  close(midM, (w * L * L) / 24, 'mid moment');
});

test('fixed-fixed, mid-span point load: PL/8', () => {
  const L = 8, P = 40;
  const r = solve(beam(L, 'fixed', 'fixed', { memberLoads: [{ member: 0, type: 'point', dir: 'gravity', value: P, a: L / 2 }] }));
  close(Math.abs(reaction(r, 0).m), (P * L) / 8, 'end moment');
  close(reaction(r, 0).fy, P / 2, 'Ry');
});

test('fixed-fixed, off-centre point load: Pab²/L² and Pa²b/L²', () => {
  const L = 10, P = 30, a = 3, b = L - a;
  const r = solve(beam(L, 'fixed', 'fixed', { memberLoads: [{ member: 0, type: 'point', dir: 'gravity', value: P, a }] }));
  close(Math.abs(reaction(r, 0).m), (P * a * b * b) / (L * L), 'left FEM');
  close(Math.abs(reaction(r, 1).m), (P * a * a * b) / (L * L), 'right FEM');
});

test('partial UDL on a simply supported beam: reactions by statics', () => {
  const L = 10, w = 8, a = 2, len = 5;
  const r = solve(beam(L, 'pinned', 'rollerY', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w, a, len }] }));
  const W = w * len, centroid = a + len / 2;
  close(reaction(r, 1).fy, (W * centroid) / L, 'Ry right');
  close(reaction(r, 0).fy, W - (W * centroid) / L, 'Ry left');
});

test('applied moment on a simply supported beam: reactions ±M/L', () => {
  const L = 5, M = 25;
  const r = solve(beam(L, 'pinned', 'rollerY', { memberLoads: [{ member: 0, type: 'moment', value: M, a: 2 }] }));
  close(reaction(r, 0).fy, M / L, 'Ry left');
  close(reaction(r, 1).fy, -M / L, 'Ry right');
});

test('propped cantilever (fixed + roller), UDL: 3wL/8 and wL²/8', () => {
  const L = 6, w = 10;
  const r = solve(beam(L, 'fixed', 'rollerY', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w }] }));
  close(reaction(r, 1).fy, (3 * w * L) / 8, 'prop reaction');
  close(Math.abs(reaction(r, 0).m), (w * L * L) / 8, 'fixed-end moment');
});

test('moment hinge in a member: fixed-pinned equals propped cantilever', () => {
  const L = 6, w = 10;
  const m = beam(L, 'fixed', 'rollerY', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w }] });
  m.members[0].relJ = true;
  const r = solve(m);
  close(reaction(r, 1).fy, (3 * w * L) / 8, 'prop reaction');
  close(Math.abs(reaction(r, 0).m), (w * L * L) / 8, 'fixed-end moment');
  close(r.members[0].endForces[5], 0, 'hinge moment');
});

test('two-span continuous beam, equal UDL: middle reaction 5wL/4', () => {
  const L = 5, w = 10;
  const r = solve({
    nodes: [{ x: 0, y: 0, support: 'pinned' }, { x: L, y: 0, support: 'rollerY' }, { x: 2 * L, y: 0, support: 'rollerY' }],
    members: [{ i: 0, j: 1, ...sec }, { i: 1, j: 2, ...sec }],
    memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w }, { member: 1, type: 'uniform', dir: 'gravity', value: w }],
  });
  close(reaction(r, 1).fy, (5 * w * L) / 4, 'middle');
  close(reaction(r, 0).fy, (3 * w * L) / 8, 'end');
});

test('triangular truss with pinned members: bar forces by statics', () => {
  const r = solve({
    nodes: [{ x: 0, y: 0, support: 'pinned' }, { x: 4, y: 0, support: 'rollerY' }, { x: 2, y: 3, support: 'free' }],
    members: [0, 1, 2].map((k) => ({ i: [0, 1, 2][k], j: [1, 2, 0][k], ...sec, relI: true, relJ: true })),
    nodeLoads: [{ node: 2, fx: 0, fy: -20, m: 0 }],
  });
  const len = Math.hypot(2, 3), force = 10 * (len / 3);
  close(r.members[1].endForces[0], force, 'inclined bar (compression: +ve at member start)', 1e-6);
  close(Math.abs(r.members[2].endForces[0]), force, 'other inclined bar', 1e-6);
  close(-r.members[0].endForces[0], 10 * (2 / 3), 'bottom chord in tension', 1e-6);
  close(r.members[0].max.N.value, 10 * (2 / 3), 'bottom chord axial diagram (tension +ve)', 1e-6);
});

test('portal frame: global equilibrium and sway', () => {
  const m = {
    nodes: [{ x: 0, y: 0, support: 'fixed' }, { x: 0, y: 4, support: 'free' }, { x: 6, y: 4, support: 'free' }, { x: 6, y: 0, support: 'fixed' }],
    members: [{ i: 0, j: 1, ...sec }, { i: 1, j: 2, ...sec }, { i: 2, j: 3, ...sec }],
    nodeLoads: [{ node: 1, fx: 15, fy: 0, m: 0 }],
    memberLoads: [{ member: 1, type: 'uniform', dir: 'gravity', value: 20 }],
  };
  const r = solve(m);
  const sx = r.reactions.reduce((s, x) => s + x.fx, 0) + 15;
  const sy = r.reactions.reduce((s, x) => s + x.fy, 0) - 20 * 6;
  const sm = r.reactions.reduce((s, x) => s + x.m + (m.nodes[x.node].x * x.fy - m.nodes[x.node].y * x.fx), 0)
    + (-20 * 6 * 3) + (-4 * 15);
  close(sx, 0, 'ΣFx'); close(sy, 0, 'ΣFy'); close(sm, 0, 'ΣM about origin', 1e-6);
  assert.ok(r.displacements[1].ux > 0, 'frame sways in load direction');
});

test('inclined cantilever gives rotated results (rigid-body invariance)', () => {
  const L = 5, P = 10, ang = Math.PI / 6;
  const flat = solve(beam(L, 'fixed', 'free', { memberLoads: [{ member: 0, type: 'point', dir: 'perp', value: -P, a: L }] }));
  const tilted = solve({
    nodes: [{ x: 0, y: 0, support: 'fixed' }, { x: L * Math.cos(ang), y: L * Math.sin(ang), support: 'free' }],
    members: [{ i: 0, j: 1, ...sec }], memberLoads: [{ member: 0, type: 'point', dir: 'perp', value: -P, a: L }],
  });
  const d = tilted.displacements[1];
  close(Math.hypot(d.ux, d.uy), Math.abs(flat.displacements[1].uy), 'tip displacement magnitude');
  close(tilted.members[0].max.M.value, flat.members[0].max.M.value, 'max moment');
});

test('gravity load on an inclined member resolves into axial + transverse', () => {
  const ang = Math.PI / 4, L = 4, w = 10;
  const r = solve({
    nodes: [{ x: 0, y: 0, support: 'pinned' }, { x: L * Math.cos(ang), y: L * Math.sin(ang), support: 'rollerY' }],
    members: [{ i: 0, j: 1, ...sec }], memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: w }],
  });
  close(r.reactions[0].fy + r.reactions[1].fy, w * L, 'ΣFy = total load');
  close(r.reactions[0].fx + r.reactions[1].fx, 0, 'ΣFx');
});

test('diagram end values agree with member end forces', () => {
  const r = solve(beam(6, 'fixed', 'rollerY', { memberLoads: [{ member: 0, type: 'uniform', dir: 'gravity', value: 10 }, { member: 0, type: 'point', dir: 'gravity', value: 15, a: 2 }] }));
  const st = r.members[0].stations, n = st.x.length - 1, f = r.members[0].endForces;
  close(st.M[0], -f[2], 'M at start');
  close(st.M[n], f[5], 'M at end', 1e-6);
  close(st.V[0], f[1], 'V at start');
  close(st.V[n], -f[4], 'V at end', 1e-6);
});

test('mechanism and bad input are reported', () => {
  assert.throws(() => solve(beam(5, 'free', 'free')), /unstable/);
  assert.throws(() => solve({ nodes: [{ x: 0, y: 0 }], members: [] }), /at least two/);
  assert.throws(() => solve({ nodes: [{ x: 0, y: 0 }, { x: 0, y: 0 }], members: [{ i: 0, j: 1, ...sec }] }), /zero length/);
});

test('500-joint chain solves quickly', () => {
  const nodes = Array.from({ length: 200 }, (_, k) => ({ x: k, y: 0, support: k === 0 ? 'fixed' : 'free' }));
  const members = nodes.slice(1).map((_, k) => ({ i: k, j: k + 1, ...sec }));
  const t = Date.now();
  solve({ nodes, members, nodeLoads: [{ node: 199, fx: 0, fy: -1, m: 0 }] });
  assert.ok(Date.now() - t < 5000, 'too slow');
});

console.log(`\n${passed} tests passed`);
