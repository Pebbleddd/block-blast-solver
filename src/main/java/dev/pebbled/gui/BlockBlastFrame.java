package dev.pebbled.gui;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.grid.Grid;
import dev.pebbled.solver.Solver;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.List;

public class BlockBlastFrame extends JFrame {
    private final int[][] grid = new int[8][8];
    private final GridPanel gridPanel;

    public BlockBlastFrame() {
        setTitle("Block Blast Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gridPanel = new GridPanel();
        gridPanel.updateFromGrid(grid);
        add(gridPanel);

        add(new PalettePanel(this::onThreeSelected), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void onThreeSelected(List<IBlock> blocks) {
        List<List<IBlock>> combinations = Solver.generateBlockCombinations(blocks);
        Grid result = Solver.getTopGrid(grid, combinations);

        if (result == null) {
            System.out.println("No valid placement — game over.");
            return;
        }

        // Print placements to console so you can read what to do on the phone
        result.getGridBlockList().forEach(p ->
                System.out.println("Place " + p.block() + " at row " + p.row() + ", col " + p.col())
        );

        // Update the persistent grid state
        int[][] newState = result.getGrid();
        for (int r = 0; r < 8; r++) {
            System.arraycopy(newState[r], 0, grid[r], 0, 8);
        }

        // Refresh the display
        gridPanel.updateFromGrid(grid);
    }
}
