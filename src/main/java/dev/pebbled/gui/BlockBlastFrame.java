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
    private final JTextArea instructionArea;
    private final JButton nextButton;
    private List<Placement> currentPlacements;
    private int currentStep;

    public BlockBlastFrame() {
        setTitle("Block Blast Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        instructionArea = new JTextArea(4, 30);
        instructionArea.setEditable(false);
        instructionArea.setBackground(new Color(40, 40, 40));
        instructionArea.setForeground(Color.WHITE);
        instructionArea.setFont(new Font("Arial", Font.BOLD, 14));
        instructionArea.setText("Select 3 blocks to place.");

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(_ -> showNextStep());
        JPanel topPanel = new JPanel(new BorderLayout(5, 0));
        topPanel.add(instructionArea, BorderLayout.CENTER);
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
            instructionArea.setText("No valid placement — game over.");
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
        GridUtil.clearCompletedRow(postStep);
        gridPanel.updateFromGrid(preStep, postStep);

        instructionArea.setText("Step " + (step + 1) + " of " + currentPlacements.size() + ":\n" + "  Place " + current.block() + " at row " + current.row() + ", col " + current.col());

        boolean hasMore = (step + 1 < currentPlacements.size());
        nextButton.setEnabled(hasMore);

        if (!hasMore) {
            for (int r = 0; r < 8; r++) {
                System.arraycopy(postStep[r], 0, this.grid[r], 0, 8);
            }
        }

    }
}
