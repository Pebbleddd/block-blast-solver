package dev.pebbled.gui;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.grid.Grid;
import dev.pebbled.grid.Placement;
import dev.pebbled.solver.Solver;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.GridUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BlockBlastFrame extends JFrame {
    private final int[][] grid = new int[8][8];
    private final GridPanel gridPanel;
    private final JButton nextButton;
    private List<Placement> currentPlacements;
    private int currentStep;
    private int roundNumber;

    public BlockBlastFrame() {
        roundNumber = 1;
        setTitle("Block Blast Solver | Round - " + roundNumber);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(_ -> showNextStep());
        JPanel topPanel = new JPanel(new BorderLayout(5, 0));
        topPanel.add(nextButton, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

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
            JOptionPane.showMessageDialog(this, "No valid placement found for the given blocks.");
            return;
        }

        currentPlacements = result.getGridBlockList();
        currentStep = 0;
        renderStep(currentStep);
    }

    private void showNextStep() {
        if (currentStep + 1 < currentPlacements.size()) {
            currentStep++;
            renderStep(currentStep);
        }
    }

    private void renderStep(int step) {
        int[][] preStep = GridUtil.cloneGrid(this.grid);
        for (int i = 0; i < step; i++) {
            Placement p = currentPlacements.get(i);
            BlockUtil.placeBlock(preStep, p.block().getShape(), p.row(), p.col());
            GridUtil.clearCompletedRow(preStep);
        }

        int[][] postStep = GridUtil.cloneGrid(preStep);
        Placement current = currentPlacements.get(step);
        BlockUtil.placeBlock(postStep, current.block().getShape(), current.row(), current.col());
        gridPanel.updateFromGrid(preStep, postStep);
        GridUtil.clearCompletedRow(postStep);

        boolean hasMore = (step + 1 < currentPlacements.size());
        nextButton.setEnabled(hasMore);

        if (!hasMore) {
            for (int r = 0; r < 8; r++) {
                System.arraycopy(postStep[r], 0, this.grid[r], 0, 8);
            }
            roundNumber++;
            setTitle("Block Blast Solver | Round - " + roundNumber);
        }

    }
}
