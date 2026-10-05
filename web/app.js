(function () {
  'use strict';
  const $ = (s, r = document) => r.querySelector(s);
  const $$ = (s, r = document) => [...r.querySelectorAll(s)];
  const W = 900, H = 540, PAD = 70;
  const SEC = { E: 200, I: 60e6, A: 600 }; // same defaults as the desktop version
  const SUP_LABEL = { free: 'Free', fixed: 'Fixed', pinned: 'Pinned', rollerY: 'Roller ↕', rollerX: 'Roller ↔' };
  const STORE = 'mbmk-frame-v1';

  // ------------------------------------------------------------------ examples
  const sec = () => ({ ...SEC });
  const EXAMPLES = {
    portal: () => ({
      nodes: [{ x: 0, y: 0, support: 'fixed' }, { x: 0, y: 4, support: 'free' }, { x: 6, y: 4, support: 'free' }, { x: 6, y: 0, support: 'fixed' }],
      members: [[0, 1], [1, 2], [2, 3]].map(([i, j]) => ({ i, j, ...sec(), E: 200, I: 400e6, A: 8000, relI: false, relJ: false })),
      nodeLoads: [{ node: 1, fx: 15, fy: 0, m: 0 }],
      memberLoads: [{ member: 1, type: 'uniform', dir: 'gravity', value: 20, a: 0, len: '' }],
    }),
    continuous: () => ({
      nodes: [{ x: 0, y: 0, support: 'pinned' }, { x: 5, y: 0, support: 'rollerY' }, { x: 10, y: 0, support: 'rollerY' }, { x: 14, y: 0, support: 'rollerY' }],
      members: [[0, 1], [1, 2], [2, 3]].map(([i, j]) => ({ i, j, E: 200, I: 400e6, A: 8000, relI: false, relJ: false })),
      nodeLoads: [],
      memberLoads: [
        { member: 0, type: 'uniform', dir: 'gravity', value: 10, a: 0, len: '' },
        { member: 1, type: 'point', dir: 'gravity', value: 40, a: 2.5, len: '' },
        { member: 2, type: 'uniform', dir: 'gravity', value: 15, a: 0, len: '' },
      ],
    }),
    truss: () => ({
      nodes: [{ x: 0, y: 0, support: 'pinned' }, { x: 4, y: 0, support: 'free' }, { x: 8, y: 0, support: 'rollerY' }, { x: 2, y: 3, support: 'free' }, { x: 6, y: 3, support: 'free' }],
      members: [[0, 1], [1, 2], [3, 4], [0, 3], [3, 1], [1, 4], [4, 2]].map(([i, j]) => ({ i, j, E: 200, I: 5e6, A: 4000, relI: true, relJ: true })),
      nodeLoads: [{ node: 3, fx: 0, fy: -20, m: 0 }, { node: 4, fx: 0, fy: -20, m: 0 }],
      memberLoads: [],
    }),
    twostory: () => ({
      nodes: [{ x: 0, y: 0, support: 'fixed' }, { x: 0, y: 3.5, support: 'free' }, { x: 0, y: 7, support: 'free' },
        { x: 6, y: 0, support: 'fixed' }, { x: 6, y: 3.5, support: 'free' }, { x: 6, y: 7, support: 'free' }],
      members: [[0, 1], [1, 2], [3, 4], [4, 5], [1, 4], [2, 5]].map(([i, j]) => ({ i, j, E: 25, I: 1.6e9, A: 150000, relI: false, relJ: false })),
      nodeLoads: [{ node: 1, fx: 20, fy: 0, m: 0 }, { node: 2, fx: 10, fy: 0, m: 0 }],
      memberLoads: [{ member: 4, type: 'uniform', dir: 'gravity', value: 25, a: 0, len: '' }, { member: 5, type: 'uniform', dir: 'gravity', value: 18, a: 0, len: '' }],
    }),
  };

  // ------------------------------------------------------------------ state
  let model = loadStored() || EXAMPLES.portal();
  let result = null, error = null;
  let view = 'model', mode = 'select', pending = null, drag = null;
  let xf = null;

  function loadStored() {
    try {
      const m = JSON.parse(localStorage.getItem(STORE));
      return sane(m) ? m : null;
    } catch (e) { return null; }
  }
  function store() { try { localStorage.setItem(STORE, JSON.stringify(model)); } catch (e) { /* storage blocked */ } }
  function sane(m) {
    return m && Array.isArray(m.nodes) && Array.isArray(m.members) && Array.isArray(m.nodeLoads) && Array.isArray(m.memberLoads);
  }

  // ------------------------------------------------------------------ helpers
  const fmt = (v, d = 2) => { const n = Number(v); if (!isFinite(n)) return '–'; const s = n.toFixed(d); return Math.abs(n) < 0.5 * 10 ** -d ? (0).toFixed(d) : s; };
  const esc = (s) => String(s).replace(/[&<>"]/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]));
  const nodePos = (i) => model.nodes[i];

  // ------------------------------------------------------------------ editable tables
  function table(el, cols, rows, opts) {
    const head = cols.map((c) => `<th>${c.label}</th>`).join('') + '<th></th>';
    const body = rows.map((row, r) => '<tr>' + cols.map((c) => {
      const dis = c.dis && c.dis(row) ? ' disabled' : '';
      const common = `data-r="${r}" data-k="${c.k}"${dis}`;
      if (c.t === 'idx') return `<td>${r + 1}</td>`;
      if (c.t === 'chk') return `<td><input type="checkbox" ${common} ${row[c.k] ? 'checked' : ''} aria-label="${c.label}"></td>`;
      if (c.t === 'sel') return `<td><select ${common}>${c.opts.map(([v, l]) => `<option value="${v}"${row[c.k] === v ? ' selected' : ''}>${l}</option>`).join('')}</select></td>`;
      if (c.t === 'ref') return `<td><select ${common}>${Array.from({ length: c.n() }, (_, i) => `<option value="${i}"${row[c.k] === i ? ' selected' : ''}>${i + 1}</option>`).join('')}</select></td>`;
      return `<td><input type="number" step="any" ${common} value="${row[c.k] === '' ? '' : row[c.k]}" ${c.ph ? `placeholder="${c.ph}"` : ''} aria-label="${c.label}"></td>`;
    }).join('') + `<td class="x"><button data-del="${r}" title="Delete" aria-label="Delete row ${r + 1}">✕</button></td></tr>`).join('');
    el.innerHTML = `<div class="scroll"><table><thead><tr>${head}</tr></thead><tbody>${body}</tbody></table></div>`;
    el.onchange = (e) => {
      const t = e.target;
      if (!t.dataset || t.dataset.k === undefined) return;
      const r = +t.dataset.r, k = t.dataset.k, col = cols.find((c) => c.k === k), row = rows[r];
      if (t.type === 'checkbox') row[k] = t.checked;
      else if (col.t === 'sel') row[k] = t.value;
      else if (col.t === 'ref') row[k] = +t.value;
      else if (t.value === '' && col.empty) row[k] = '';
      else if (t.value !== '' && isFinite(+t.value)) row[k] = +t.value;
      else t.value = row[k];
      if (col.rerender) renderTables();
      refresh();
    };
    el.onclick = (e) => {
      const b = e.target.closest('[data-del]');
      if (b) { opts.remove(+b.dataset.del); pending = null; renderTables(); refresh(); }
    };
  }

  function renderTables() {
    const nn = () => model.nodes.length, mn = () => model.members.length;
    table($('#t-nodes'), [
      { t: 'idx', label: '#' }, { k: 'x', label: 'x' }, { k: 'y', label: 'y' },
      { k: 'support', label: 'Support', t: 'sel', opts: Object.entries(SUP_LABEL) },
    ], model.nodes, { remove: removeNode });
    table($('#t-members'), [
      { t: 'idx', label: '#' }, { k: 'i', label: 'From', t: 'ref', n: nn }, { k: 'j', label: 'To', t: 'ref', n: nn },
      { k: 'E', label: 'E GPa' }, { k: 'I', label: 'I mm⁴' }, { k: 'A', label: 'A mm²' },
      { k: 'relI', label: 'Hinge 1', t: 'chk' }, { k: 'relJ', label: 'Hinge 2', t: 'chk' },
    ], model.members, { remove: removeMember });
    table($('#t-nodeloads'), [
      { t: 'idx', label: '#' }, { k: 'node', label: 'Joint', t: 'ref', n: nn },
      { k: 'fx', label: 'Fx kN' }, { k: 'fy', label: 'Fy kN' }, { k: 'm', label: 'M kN·m' },
    ], model.nodeLoads, { remove: (i) => model.nodeLoads.splice(i, 1) });
    table($('#t-memberloads'), [
      { t: 'idx', label: '#' }, { k: 'member', label: 'Member', t: 'ref', n: mn },
      { k: 'type', label: 'Type', t: 'sel', opts: [['uniform', 'Uniform'], ['point', 'Point'], ['moment', 'Moment']], rerender: true },
      { k: 'dir', label: 'Direction', t: 'sel', opts: [['gravity', 'Gravity ↓'], ['perp', 'Perpendicular'], ['axial', 'Along member']], dis: (r) => r.type === 'moment' },
      { k: 'value', label: 'Value' }, { k: 'a', label: 'a m' },
      { k: 'len', label: 'Length m', empty: true, ph: 'to end', dis: (r) => r.type !== 'uniform' },
    ], model.memberLoads, { remove: (i) => model.memberLoads.splice(i, 1) });
  }

  function removeNode(k) {
    const fix = (v) => (v > k ? v - 1 : v);
    const newIndex = [];
    let kept = 0;
    model.members.forEach((m, idx) => { newIndex[idx] = m.i === k || m.j === k ? -1 : kept++; });
    model.members = model.members.filter((m) => m.i !== k && m.j !== k);
    model.members.forEach((m) => { m.i = fix(m.i); m.j = fix(m.j); });
    model.memberLoads = model.memberLoads.filter((l) => newIndex[l.member] >= 0);
    model.memberLoads.forEach((l) => (l.member = newIndex[l.member]));
    model.nodes.splice(k, 1);
    model.nodeLoads = model.nodeLoads.filter((l) => l.node !== k);
    model.nodeLoads.forEach((l) => (l.node = fix(l.node)));
  }
  function removeMember(k) {
    model.members.splice(k, 1);
    model.memberLoads = model.memberLoads.filter((l) => l.member !== k);
    model.memberLoads.forEach((l) => { if (l.member > k) l.member--; });
  }

  // ------------------------------------------------------------------ solve + render
  function refresh() {
    store();
    try { result = FrameSolver.solve(model); error = null; } catch (e) { result = null; error = e.message; }
    render();
    renderResults();
  }

  function bounds() {
    let x0 = Infinity, y0 = Infinity, x1 = -Infinity, y1 = -Infinity;
    for (const n of model.nodes) { x0 = Math.min(x0, n.x); x1 = Math.max(x1, n.x); y0 = Math.min(y0, n.y); y1 = Math.max(y1, n.y); }
    if (!isFinite(x0)) { x0 = 0; x1 = 10; y0 = 0; y1 = 6; }
    const minW = 10, minH = 6;
    if (x1 - x0 < minW) { const c = (x0 + x1) / 2; x0 = c - minW / 2; x1 = c + minW / 2; }
    if (y1 - y0 < minH) { const c = (y0 + y1) / 2; y0 = c - minH / 2; y1 = c + minH / 2; }
    return { x0, y0, x1, y1 };
  }
  function makeXf() {
    const b = bounds();
    const sc = Math.min((W - 2 * PAD) / (b.x1 - b.x0), (H - 2 * PAD) / (b.y1 - b.y0));
    const ox = (W - sc * (b.x1 - b.x0)) / 2 - sc * b.x0, oy = H - (H - sc * (b.y1 - b.y0)) / 2 + sc * b.y0;
    return { sc, X: (x) => ox + sc * x, Y: (y) => oy - sc * y, ix: (px) => (px - ox) / sc, iy: (py) => (oy - py) / sc, extent: Math.max(b.x1 - b.x0, b.y1 - b.y0) };
  }

  const P = (n) => [xf.X(n.x), xf.Y(n.y)];
  const f1 = (v) => (+v).toFixed(1);

  function arrow(x0, y0, x1, y1, color, w = 1.8) {
    const dx = x1 - x0, dy = y1 - y0, l = Math.hypot(dx, dy) || 1, ux = dx / l, uy = dy / l, s = 6;
    return `<path d="M${f1(x0)} ${f1(y0)}L${f1(x1)} ${f1(y1)}" stroke="${color}" stroke-width="${w}" fill="none"/>` +
      `<path d="M${f1(x1)} ${f1(y1)}l${f1(-ux * s - uy * s * 0.5)} ${f1(-uy * s + ux * s * 0.5)}l${f1(uy * s)} ${f1(-ux * s)}z" fill="${color}"/>`;
  }
  function momentArc(px, py, ccw, color, r = 16) {
    const pt = (deg) => [px + r * Math.cos((deg * Math.PI) / 180), py - r * Math.sin((deg * Math.PI) / 180)];
    const [a0, a1] = ccw ? [20, 290] : [290, 20];
    const s = pt(a0), e = pt(a1);
    const th = (a1 * Math.PI) / 180, sign = ccw ? 1 : -1;
    const tx = -Math.sin(th) * sign, ty = -Math.cos(th) * sign; // tangent on screen
    return `<path d="M${f1(s[0])} ${f1(s[1])}A${r} ${r} 0 1 ${ccw ? 0 : 1} ${f1(e[0])} ${f1(e[1])}" stroke="${color}" stroke-width="1.8" fill="none"/>` +
      arrow(e[0] - tx * 4, e[1] - ty * 4, e[0] + tx * 2, e[1] + ty * 2, color, 0.1);
  }
  function support(n) {
    const [x, y] = P(n), c = 'var(--struct)', g = 'var(--faint)';
    switch (n.support) {
      case 'fixed': return `<g><rect x="${x - 11}" y="${y - 11}" width="22" height="22" fill="${g}" fill-opacity=".45" stroke="${c}"/><path d="M${x - 11} ${y + 3}l14 -14M${x - 11} ${y + 11}l22 -22M${x - 3} ${y + 11}l14 -14" stroke="${c}" stroke-width="1"/></g>`;
      case 'pinned': return `<g><path d="M${x} ${y}l-11 18h22z" fill="${g}" fill-opacity=".45" stroke="${c}"/><path d="M${x - 15} ${y + 18}h30" stroke="${c}"/></g>`;
      case 'rollerY': return `<g><path d="M${x} ${y}l-11 15h22z" fill="${g}" fill-opacity=".45" stroke="${c}"/><circle cx="${x - 5}" cy="${y + 18}" r="3" fill="none" stroke="${c}"/><circle cx="${x + 5}" cy="${y + 18}" r="3" fill="none" stroke="${c}"/><path d="M${x - 15} ${y + 21}h30" stroke="${c}"/></g>`;
      case 'rollerX': return `<g transform="rotate(90 ${x} ${y})"><path d="M${x} ${y}l-11 15h22z" fill="${g}" fill-opacity=".45" stroke="${c}"/><circle cx="${x - 5}" cy="${y + 18}" r="3" fill="none" stroke="${c}"/><circle cx="${x + 5}" cy="${y + 18}" r="3" fill="none" stroke="${c}"/><path d="M${x - 15} ${y + 21}h30" stroke="${c}"/></g>`;
      default: return '';
    }
  }
  function memberGeom(m) {
    const a = nodePos(m.i), b = nodePos(m.j);
    const L = Math.hypot(b.x - a.x, b.y - a.y) || 1;
    return { a, b, L, c: (b.x - a.x) / L, s: (b.y - a.y) / L };
  }
  const worldToPx = (g, x, y) => [xf.X(g.a.x + x * g.c - y * g.s), xf.Y(g.a.y + x * g.s + y * g.c)];

  function drawLoads() {
    const col = 'var(--load)';
    let out = '';
    for (const l of model.nodeLoads) {
      const n = nodePos(l.node); if (!n) continue;
      const [x, y] = P(n);
      if (l.fx) { const d = Math.sign(l.fx); out += arrow(x - d * 46, y, x - d * 4, y, col) + `<text x="${x - d * 46}" y="${y - 6}" text-anchor="${d > 0 ? 'end' : 'start'}">${fmt(l.fx, 1)}</text>`; }
      if (l.fy) { const d = Math.sign(l.fy); out += arrow(x, y + d * 46, x, y + d * 4, col) + `<text x="${x + 6}" y="${y + d * 46 + (d > 0 ? 10 : -3)}">${fmt(l.fy, 1)}</text>`; }
      if (l.m) out += momentArc(x, y, l.m > 0, col, 18) + `<text x="${x + 20}" y="${y - 16}">${fmt(l.m, 1)}</text>`;
    }
    for (const l of model.memberLoads) {
      const m = model.members[l.member]; if (!m || !nodePos(m.i) || !nodePos(m.j)) continue;
      const g = memberGeom(m), v = Number(l.value) || 0; if (!v) continue;
      const a = Math.min(Math.max(Number(l.a) || 0, 0), g.L);
      if (l.type === 'moment') {
        const [x, y] = worldToPx(g, a, 0);
        out += momentArc(x, y, v > 0, col, 14) + `<text x="${x + 16}" y="${y - 14}">${fmt(v, 1)}</text>`;
        continue;
      }
      // arrow direction in world coords
      let dir = l.dir === 'perp' ? [-g.s, g.c] : l.dir === 'axial' ? [g.c, g.s] : [0, -1];
      const sg = l.dir === 'gravity' ? Math.sign(v) : Math.sign(v);
      dir = [dir[0] * sg, dir[1] * sg];
      const dpx = [dir[0], -dir[1]]; // pixel direction
      const nOff = l.dir === 'axial' ? [-g.s * 8, -g.c * 8] : [0, 0];
      if (l.type === 'point') {
        const [x, y] = worldToPx(g, a, 0), len = 40;
        out += arrow(x - dpx[0] * len + nOff[0], y - dpx[1] * len + nOff[1], x + nOff[0], y + nOff[1], col, 2) +
          `<text x="${x - dpx[0] * len + 4}" y="${y - dpx[1] * len - 4}">${fmt(v, 1)}</text>`;
      } else {
        const lenM = l.len === '' || l.len == null ? g.L - a : Math.min(Math.max(Number(l.len), 0), g.L - a);
        if (lenM <= 0) continue;
        const [x0, y0] = worldToPx(g, a, 0), [x1, y1] = worldToPx(g, a + lenM, 0);
        const cnt = Math.max(2, Math.round(Math.hypot(x1 - x0, y1 - y0) / 22)), len = l.dir === 'axial' ? 16 : 24;
        const tails = [];
        for (let k = 0; k <= cnt; k++) {
          const t = k / cnt, x = x0 + (x1 - x0) * t + nOff[0], y = y0 + (y1 - y0) * t + nOff[1];
          tails.push([x - dpx[0] * len, y - dpx[1] * len]);
          out += arrow(x - dpx[0] * len, y - dpx[1] * len, x, y, col, 1.4);
        }
        out += `<path d="M${tails.map((p) => f1(p[0]) + ' ' + f1(p[1])).join('L')}" stroke="${col}" fill="none" stroke-width="1.4"/>`;
        const mid = tails[Math.floor(tails.length / 2)];
        out += `<text x="${f1(mid[0])}" y="${f1(mid[1] - 5)}" text-anchor="middle">${fmt(v, 1)}</text>`;
      }
    }
    return out;
  }

  function drawStructure(opts = {}) {
    let out = '';
    model.members.forEach((m, k) => {
      const a = nodePos(m.i), b = nodePos(m.j); if (!a || !b) return;
      const [x0, y0] = P(a), [x1, y1] = P(b);
      const col = opts.faint ? 'var(--faint)' : 'var(--struct)';
      let tip = `Member ${k + 1}`;
      const r = result && result.members[k];
      if (r) tip += ` — Nmax ${fmt(r.max.N.value)} kN, Vmax ${fmt(r.max.V.value)} kN, Mmax ${fmt(r.max.M.value)} kN·m`;
      out += `<g><title>${tip}</title><path d="M${f1(x0)} ${f1(y0)}L${f1(x1)} ${f1(y1)}" stroke="${col}" stroke-width="${opts.faint ? 1.5 : 3.5}" stroke-linecap="round"/>`;
      if (!opts.faint) {
        out += `<text x="${f1((x0 + x1) / 2 + 9)}" y="${f1((y0 + y1) / 2 - 6)}" fill="var(--muted)" style="fill:var(--muted)">M${k + 1}</text>`;
        if (m.relI) out += `<circle cx="${f1(x0 + (x1 - x0) * 0.04 + (x1 > x0 ? 0 : 0))}" cy="${f1(y0 + (y1 - y0) * 0.04)}" r="4" fill="var(--panel)" stroke="var(--struct)"/>`;
        if (m.relJ) out += `<circle cx="${f1(x1 - (x1 - x0) * 0.04)}" cy="${f1(y1 - (y1 - y0) * 0.04)}" r="4" fill="var(--panel)" stroke="var(--struct)"/>`;
      }
      out += '</g>';
    });
    model.nodes.forEach((n, i) => {
      const [x, y] = P(n);
      out += support(n);
      out += `<circle data-node="${i}" cx="${f1(x)}" cy="${f1(y)}" r="${opts.faint ? 3 : 6}" fill="${pending === i ? 'var(--accent)' : 'var(--panel)'}" stroke="var(--accent)" stroke-width="2" style="cursor:${mode === 'select' ? 'grab' : 'crosshair'}"><title>Joint ${i + 1} (${fmt(n.x)}, ${fmt(n.y)})</title></circle>`;
      if (!opts.faint) out += `<text x="${f1(x - 10)}" y="${f1(y - 10)}" text-anchor="end" style="fill:var(--accent);font-weight:600">${i + 1}</text>`;
    });
    return out;
  }

  function drawDiagram(kind) {
    const key = { axial: 'N', shear: 'V', moment: 'M' }[kind];
    const unit = kind === 'moment' ? 'kN·m' : 'kN';
    let max = 0;
    result.members.forEach((m) => m.stations[key].forEach((v) => (max = Math.max(max, Math.abs(v)))));
    if (max < 1e-9) return `<text x="${W / 2}" y="40" text-anchor="middle">Nothing to show — all ${kind} values are zero.</text>`;
    const k = (0.13 * Math.min(W, H)) / max;
    const sign = kind === 'moment' ? -1 : 1; // moment is plotted on the tension side
    let out = '';
    const labels = [];
    for (const r of result.members) {
      const a = nodePos(r.i), b = nodePos(r.j), [x0, y0] = P(a);
      const nx = -r.s, ny = -r.c; // local +y on screen
      const st = r.stations, pts = [];
      st.x.forEach((x, idx) => {
        const bx = x0 + r.c * x * xf.sc, by = y0 - r.s * x * xf.sc;
        const o = sign * st[key][idx] * k;
        pts.push([bx + nx * o, by + ny * o, bx, by, st[key][idx], x]);
      });
      const poly = pts.map((p) => `${f1(p[0])},${f1(p[1])}`).join(' ');
      const base = `${f1(pts[pts.length - 1][2])},${f1(pts[pts.length - 1][3])} ${f1(pts[0][2])},${f1(pts[0][3])}`;
      out += `<polygon points="${poly} ${base}" fill="var(--pos)" fill-opacity=".16" stroke="none"/>`;
      // positive and negative parts get different colours by drawing the outline in segments
      for (let i = 1; i < pts.length; i++) {
        const v = (pts[i][4] + pts[i - 1][4]) / 2;
        out += `<path d="M${f1(pts[i - 1][0])} ${f1(pts[i - 1][1])}L${f1(pts[i][0])} ${f1(pts[i][1])}" stroke="${v >= 0 ? 'var(--pos)' : 'var(--neg)'}" stroke-width="2"/>`;
      }
      const want = [pts[0], pts[pts.length - 1]];
      let pk = pts[0]; pts.forEach((p) => { if (Math.abs(p[4]) > Math.abs(pk[4])) pk = p; });
      want.push(pk);
      for (const p of want) if (Math.abs(p[4]) > 0.02 * max) labels.push(p);
    }
    const placed = [];
    for (const p of labels) {
      if (placed.some((q) => Math.hypot(q[0] - p[0], q[1] - p[1]) < 26)) continue;
      placed.push(p);
      out += `<text x="${f1(p[0])}" y="${f1(p[1] - 6)}" text-anchor="middle" style="font-weight:600">${fmt(p[4])}</text>`;
    }
    const note = kind === 'moment' ? 'Moment (kN·m), drawn on the tension side · sagging +' : kind === 'shear' ? 'Shear force (kN)' : 'Axial force (kN), tension +';
    return out + `<text x="14" y="${H - 14}" style="fill:var(--muted)">${note}</text>`;
  }

  function drawDeformed() {
    let maxD = 0;
    result.displacements.forEach((d) => (maxD = Math.max(maxD, Math.hypot(d.ux, d.uy))));
    result.members.forEach((r) => r.deflection.v.forEach((v) => (maxD = Math.max(maxD, Math.abs(v)))));
    const auto = maxD > 0 ? (0.08 * xf.extent) / maxD : 1;
    const scale = auto * Number($('#scale').value);
    let out = '';
    for (const r of result.members) {
      const a = nodePos(r.i), d = r.deflection;
      const pts = d.x.map((x, i) => {
        const lx = x + scale * d.u[i], ly = scale * d.v[i];
        return `${f1(xf.X(a.x + lx * r.c - ly * r.s))},${f1(xf.Y(a.y + lx * r.s + ly * r.c))}`;
      });
      out += `<polyline points="${pts.join(' ')}" fill="none" stroke="var(--accent)" stroke-width="3" stroke-linejoin="round"/>`;
    }
    return out + `<text x="14" y="${H - 14}" style="fill:var(--muted)">Deformation exaggerated ×${fmt(scale, scale < 10 ? 1 : 0)} · max displacement ${fmt(maxD * 1000)} mm</text>`;
  }

  function render() {
    xf = makeXf();
    const svg = $('#canvas');
    let out = '';
    if (mode === 'draw') out += drawGrid();
    const showResult = result && view !== 'model';
    if (showResult && view === 'deformed') out += drawStructure({ faint: true }) + drawDeformed();
    else if (showResult) out += drawStructure({ faint: true }) + drawDiagram(view);
    else out += drawStructure() + drawLoads();
    svg.innerHTML = out;
    svg.classList.toggle('draw', mode === 'draw');
    $('#scale-wrap').hidden = !(showResult && view === 'deformed');
    $('#draw-hint').hidden = mode !== 'draw';
    const msgs = [];
    if (error) msgs.push(`<div class="err">${esc(error)}</div>`);
    if (result) result.warnings.forEach((w) => msgs.push(`<div class="warn">${esc(w)}</div>`));
    $('#msg').innerHTML = msgs.join('');
  }

  function drawGrid() {
    const step = Math.max(Number($('#snap').value) || 0.5, 0.05);
    const x0 = xf.ix(0), x1 = xf.ix(W), y0 = xf.iy(H), y1 = xf.iy(0);
    const cols = Math.ceil((x1 - x0) / step), rows = Math.ceil((y1 - y0) / step);
    if (cols * rows > 4000) return '';
    let out = '<g fill="var(--grid)">';
    for (let i = Math.floor(x0 / step); i <= x1 / step; i++) for (let j = Math.floor(y0 / step); j <= y1 / step; j++) out += `<circle cx="${f1(xf.X(i * step))}" cy="${f1(xf.Y(j * step))}" r="1.6"/>`;
    return out + '</g>';
  }

  // ------------------------------------------------------------------ results tables
  function renderResults() {
    const ok = !!result;
    const empty = '<p class="hint">Fix the input to see results.</p>';
    if (!ok) { $('#r-reactions').innerHTML = $('#r-disp').innerHTML = $('#r-members').innerHTML = empty; return; }
    const rows = (head, body) => `<div class="scroll"><table class="res"><thead><tr>${head.map((h) => `<th>${h}</th>`).join('')}</tr></thead><tbody>${body.map((r) => `<tr>${r.map((c) => `<td>${c}</td>`).join('')}</tr>`).join('')}</tbody></table></div>`;
    $('#r-reactions').innerHTML = result.reactions.length ? rows(['Joint', 'Rx', 'Ry', 'M'], result.reactions.map((r) => [r.node + 1, fmt(r.fx), fmt(r.fy), fmt(r.m)])) : '<p class="hint">No supports.</p>';
    $('#r-disp').innerHTML = rows(['Joint', 'ux', 'uy', 'θ'], result.displacements.map((d, i) => [i + 1, fmt(d.ux * 1000, 3), fmt(d.uy * 1000, 3), fmt(d.rz * 1000, 3)]));
    $('#r-members').innerHTML = rows(['Member', 'N max', 'V max', 'M max', 'M max at x', 'M start', 'M end'], result.members.map((r) => {
      const st = r.stations, n = st.x.length - 1;
      return [r.index + 1, fmt(r.max.N.value), fmt(r.max.V.value), fmt(r.max.M.value), fmt(r.max.M.x) + ' m', fmt(st.M[0]), fmt(st.M[n])];
    }));
  }

  // ------------------------------------------------------------------ interaction
  function svgPoint(e) {
    const svg = $('#canvas'), r = svg.getBoundingClientRect(), s = Math.min(r.width / W, r.height / H);
    const px = (e.clientX - r.left - (r.width - W * s) / 2) / s, py = (e.clientY - r.top - (r.height - H * s) / 2) / s;
    const snap = Number($('#snap').value) || 0;
    let x = xf.ix(px), y = xf.iy(py);
    if (snap > 0) { x = Math.round(x / snap) * snap; y = Math.round(y / snap) * snap; }
    return { px, py, x: +x.toFixed(6), y: +y.toFixed(6) };
  }
  function nearestNode(px, py, r = 14) {
    let best = -1, bd = r;
    model.nodes.forEach((n, i) => { const d = Math.hypot(xf.X(n.x) - px, xf.Y(n.y) - py); if (d < bd) { bd = d; best = i; } });
    return best;
  }

  const svg = $('#canvas');
  svg.addEventListener('click', (e) => {
    if (mode !== 'draw') return;
    const p = svgPoint(e);
    let idx = nearestNode(p.px, p.py);
    if (idx < 0) { model.nodes.push({ x: p.x, y: p.y, support: 'free' }); idx = model.nodes.length - 1; }
    if (pending !== null && pending !== idx && !model.members.some((m) => (m.i === pending && m.j === idx) || (m.i === idx && m.j === pending))) {
      model.members.push({ i: pending, j: idx, ...sec(), relI: false, relJ: false });
    }
    pending = idx;
    renderTables(); refresh();
  });
  svg.addEventListener('contextmenu', (e) => { if (mode === 'draw') { e.preventDefault(); pending = null; render(); } });
  svg.addEventListener('pointerdown', (e) => {
    if (mode !== 'select' || view !== 'model') return;
    const t = e.target.closest('[data-node]');
    if (!t) return;
    drag = +t.dataset.node;
    svg.setPointerCapture(e.pointerId);
    e.preventDefault();
  });
  svg.addEventListener('pointermove', (e) => {
    if (drag === null) return;
    const p = svgPoint(e);
    model.nodes[drag].x = p.x; model.nodes[drag].y = p.y;
    refreshNoBounds();
  });
  const endDrag = () => { if (drag !== null) { drag = null; renderTables(); refresh(); } };
  svg.addEventListener('pointerup', endDrag);
  svg.addEventListener('pointercancel', endDrag);
  // While dragging keep the same view transform so the joint stays under the pointer.
  function refreshNoBounds() {
    store();
    try { result = FrameSolver.solve(model); error = null; } catch (e) { result = null; error = e.message; }
    $('#canvas').innerHTML = drawStructure() + drawLoads();
    renderResults();
  }
  document.addEventListener('keydown', (e) => { if (e.key === 'Escape') { pending = null; render(); } });

  $$('.tabs button').forEach((b) => b.addEventListener('click', () => {
    $$('.tabs button').forEach((x) => x.classList.toggle('on', x === b));
    $$('[data-panel]').forEach((p) => (p.hidden = p.dataset.panel !== b.dataset.tab));
  }));
  $$('#views button').forEach((b) => b.addEventListener('click', () => {
    view = b.dataset.view;
    $$('#views button').forEach((x) => x.classList.toggle('on', x === b));
    render();
  }));
  $$('#modes button').forEach((b) => b.addEventListener('click', () => {
    mode = b.dataset.mode; pending = null;
    $$('#modes button').forEach((x) => x.classList.toggle('on', x === b));
    if (mode === 'draw' && view !== 'model') { view = 'model'; $$('#views button').forEach((x) => x.classList.toggle('on', x.dataset.view === 'model')); }
    render();
  }));
  $('#snap').addEventListener('change', render);
  $('#scale').addEventListener('input', render);

  $('#add-node').onclick = () => { const l = model.nodes[model.nodes.length - 1]; model.nodes.push({ x: l ? l.x + 4 : 0, y: l ? l.y : 0, support: 'free' }); renderTables(); refresh(); };
  $('#add-member').onclick = () => { const n = model.nodes.length; model.members.push({ i: Math.max(0, n - 2), j: Math.max(0, n - 1), ...sec(), relI: false, relJ: false }); renderTables(); refresh(); };
  $('#add-nodeload').onclick = () => { model.nodeLoads.push({ node: 0, fx: 0, fy: -10, m: 0 }); renderTables(); refresh(); };
  $('#add-memberload').onclick = () => { model.memberLoads.push({ member: 0, type: 'uniform', dir: 'gravity', value: 10, a: 0, len: '' }); renderTables(); refresh(); };

  function replaceModel(m) { model = m; pending = null; renderTables(); refresh(); }
  $('#example').onchange = (e) => { if (e.target.value) replaceModel(EXAMPLES[e.target.value]()); e.target.value = ''; };
  $('#btn-new').onclick = () => { if (confirm('Start a new, empty model?')) { replaceModel({ nodes: [], members: [], nodeLoads: [], memberLoads: [] }); $$('#modes button')[1].click(); } };
  $('#btn-print').onclick = () => window.print();
  $('#btn-export').onclick = () => {
    const a = document.createElement('a');
    a.href = URL.createObjectURL(new Blob([JSON.stringify(model, null, 2)], { type: 'application/json' }));
    a.download = 'frame.json'; a.click(); setTimeout(() => URL.revokeObjectURL(a.href), 1000);
  };
  $('#file-open').onchange = async (e) => {
    const f = e.target.files[0]; e.target.value = ''; if (!f) return;
    try { const m = JSON.parse(await f.text()); if (!sane(m)) throw 0; replaceModel(m); }
    catch (err) { $('#msg').innerHTML = '<div class="err">That file is not a saved frame (.json).</div>'; }
  };
  // Import the two text files used by the desktop version (joints.txt: "x,y"; members.txt: "i,j", 0-based).
  $('#file-txt').onchange = async (e) => {
    const files = [...e.target.files]; e.target.value = '';
    const read = async (re) => { const f = files.find((x) => re.test(x.name)); return f ? (await f.text()).split(/\r?\n/).map((l) => l.trim().split(/[\s,;]+/).map(Number)).filter((r) => r.length >= 2 && r.every(isFinite)) : []; };
    const joints = await read(/joint/i), members = await read(/member/i);
    if (!joints.length || !members.length) { $('#msg').innerHTML = '<div class="err">Select both joints.txt and members.txt together.</div>'; return; }
    replaceModel({
      nodes: joints.map(([x, y]) => ({ x, y, support: 'free' })),
      members: members.map(([i, j]) => ({ i, j, ...sec(), relI: false, relJ: false })).filter((m) => joints[m.i] && joints[m.j]),
      nodeLoads: [], memberLoads: [],
    });
  };

  renderTables();
  refresh();
})();
