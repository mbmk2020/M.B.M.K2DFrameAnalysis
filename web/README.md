# M.B.M.K 2D Frame Analysis — web version

A zero-install rewrite of the desktop app. Open `index.html` in any browser (no server, no build, no Java)
or host this folder on any static host such as GitHub Pages.

## What it does
- 2D frames, trusses and beams by the direct stiffness method (beam-column elements, 3 DOF per joint).
- Supports: fixed, pinned, roller (vertical / horizontal). Member-end hinges (moment releases).
- Loads: joint forces and moments; member uniform (full or partial), point and moment loads;
  direction = gravity, perpendicular to the member, or along the member.
- Results update live: reactions, displacements, deformed shape, axial / shear / moment diagrams, member maxima.
- Draw by clicking the canvas (snap grid), drag joints, edit in tables. Save/open `.json`,
  import the desktop version's `joints.txt` + `members.txt`, print to PDF. Autosaves in the browser.

## Units
Lengths m · forces kN · moments kN·m · E in GPa · I in mm⁴ · A in mm².
Sign conventions: +x right, +y up, counter-clockwise positive; tension and sagging moment positive.

## Tests
`node web/solver.test.js` — checks the solver against closed-form textbook results
(cantilever, simply supported, fixed-fixed, propped and continuous beams, truss, hinges,
inclined members, equilibrium). No dependencies.

## Layout
| file | purpose |
|---|---|
| `solver.js` | pure solver, no DOM; usable from Node or the browser (`FrameSolver.solve(model)`) |
| `app.js` | UI: tables, SVG drawing, interaction |
| `index.html`, `style.css` | page and styles (light/dark) |

Not ported from the desktop version: the reinforced-concrete design / cost estimator.
