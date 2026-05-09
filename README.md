# Block Blast Solver

An advisor for the mobile puzzle game **Block Blast**. You play the game on your phone; this program tells you the optimal way to place each set of 3 blocks the game deals you, on an 8×8 grid.

The program supports **any shape** — when the game gives you a block, you draw it on the canvas and the solver handles it. There's no fixed block palette to keep in sync with the game's set.

## How it works

1. Open Block Blast on your phone.
2. Run the solver.
3. Click **Draw Custom Shapes**.
4. For each of the 3 blocks the game just dealt you: click cells on the canvas to draw the shape, then click **Done**.
5. After the third Done, the view returns to the main grid and the solver computes the optimal placement order.
6. The grid walks you through the placements one at a time. Each step shows where to place the current block (highlighted in green) with the rest of the grid in carried-over magenta. Click **Next** to advance to the next placement.
7. Mimic each placement on your phone as the solver advances through them.
8. Once you've completed all three placements, click **Draw Custom Shapes** again to begin the next round.

The solver auto-detects game-over (no valid placement exists for the 3 shapes you drew) and surfaces a dialog.

## Screenshot

![Block Blast Solver GUI](assets/img.png)

## How the solver works

For the 3 blocks you drew, the solver:

1. Generates all 6 permutations (orderings) of the blocks.
2. For each permutation, brute-force tries every `(row, col)` anchor for the first block, then expands the search to every anchor for the second, then the third — keeping only sequences where all three blocks fit.
3. Each candidate end-state grid is scored:
   - `score = clearedCells × 0.5 + largestConnectedEmptyArea × 0.25`
4. The placement sequence with the highest score wins.

Score weighting prioritizes actually clearing rows/columns (0.5) over preserving open space (0.25). The largest empty area is computed via flood fill.

## Requirements

- **Java 22** or newer
- **Maven** (for command-line builds — optional if you run from IntelliJ)

## Running

### From IntelliJ IDEA

Open the project as a Maven project and run `dev.pebbled.Main`.

### From the command line

```
mvn compile
java -cp target/classes dev.pebbled.Main
```

## Project structure

```
src/main/java/dev/pebbled/
├── Main.java                  # Entry point — launches BlockBlastFrame on the EDT
├── algorithm/
│   └── FloodFill.java         # Connected-region area calculation
├── blocks/
│   ├── IBlock.java            # Block contract: returns its boolean[][] shape
│   └── impl/
│       └── CustomBlock.java   # Wraps a user-drawn shape as an IBlock
├── grid/
│   ├── Grid.java              # Wraps an int[][] grid + placement history
│   └── Placement.java         # (block, row, col) record
├── gui/
│   ├── BlockBlastFrame.java   # JFrame — owns grid state, swaps views via CardLayout
│   ├── GridPanel.java         # 8×8 grid display with diff-colored cells
│   └── DrawingPanel.java      # 8×8 clickable canvas for drawing shapes
├── solver/
│   └── Solver.java            # getTopGrid + generateBlockCombinations + helpers
└── utils/
    ├── BlockUtil.java         # placeBlock(grid, shape, row, col)
    └── GridUtil.java          # isSpaceEmpty, clearCompletedRow, scoring, printing
```

## Current limitations

- **Empty starting grid.** Each session starts with an empty 8×8 grid — you have to start the solver at the same time you start a fresh game on your phone.
- **No save/load.** Closing the program loses the in-memory grid state; you can't pick up an in-progress game between sessions.

## Roadmap

- [x] Swing GUI: visual grid renderer, custom-shape drawing canvas, step-through walkthrough
- [x] GUI polish: in-window placement display, distinct color for new placements vs. carried-over cells, round counter
- [x] Custom shape input — user draws each block on an 8×8 canvas
- [ ] Manual grid input on startup (so the solver can pick up an in-progress game)
- [ ] Save/load grid state across sessions
- [ ] GUI overhaul: Make the GUI not just a base Swing GUI with no styling.

## Author

Built by [Pebbleddd](https://github.com/Pebbleddd).
