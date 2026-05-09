package dev.pebbled.gui;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.blocks.impl.CustomBlock;
import dev.pebbled.grid.Grid;
import dev.pebbled.grid.Placement;
import dev.pebbled.solver.Solver;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.GridUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockBlastFrame extends JFrame {
    private final int[][] grid = new int[8][8];
    private final GridPanel gridPanel;
    private final JButton nextButton;
    private List<Placement> currentPlacements;
    private int currentStep;
    private int roundNumber;

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final DrawingPanel drawingPanel;
    private final JLabel drawingInstructions;
    private final JTextArea instructionsArea;
    private final List<IBlock> drawnShapes = new ArrayList<>();

    public BlockBlastFrame() {
        roundNumber = 1;
        setTitle("Block Blast Solver | Round - " + roundNumber);
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingInstructions = new JLabel("Shape 1 of 3 — click cells, then click Done.", SwingConstants.CENTER);

        instructionsArea = new JTextArea(4, 30);
        instructionsArea.setEditable(false);
        instructionsArea.setBackground(new Color(40, 40, 40));
        instructionsArea.setForeground(Color.WHITE);
        instructionsArea.setFont(new Font("Arial", Font.BOLD, 14));
        instructionsArea.setText("Select 3 blocks to place.");

        JPanel mainView = new JPanel(new BorderLayout());
        JButton drawCustomButton = new JButton("Draw Custom Shapes");
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(_ -> showNextStep());

        drawCustomButton.addActionListener(_ -> enterDrawingMode());

        JPanel topPanel = new JPanel(new BorderLayout(5, 0));
        topPanel.add(nextButton, BorderLayout.EAST);
        topPanel.add(instructionsArea, BorderLayout.CENTER);
        topPanel.add(drawCustomButton, BorderLayout.WEST);

        mainView.add(topPanel, BorderLayout.NORTH);

        gridPanel = new GridPanel();
        gridPanel.updateFromGrid(grid);
        mainView.add(gridPanel, BorderLayout.CENTER);

        drawingPanel = new DrawingPanel();
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(_ -> onDrawingDone());

        JPanel drawingView = new JPanel(new BorderLayout());
        drawingView.add(drawingPanel, BorderLayout.CENTER);
        drawingView.add(doneButton, BorderLayout.SOUTH);
        drawingView.add(drawingInstructions, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(mainView, "main");
        cardPanel.add(drawingView, "drawing");

        add(cardPanel);

        setVisible(true);
    }

    private void enterDrawingMode() {
        drawnShapes.clear();
        drawingPanel.clear();
        drawingInstructions.setText("Shape 1 of 3 — click cells, then click Done.");
        cardLayout.show(cardPanel, "drawing");
    }

    private void onDrawingDone() {
        boolean[][] shape = drawingPanel.getShape();
        if (shape == null) {
            drawingInstructions.setText("Empty shape — draw at least one cell.");
            return;
        }

        drawnShapes.add(new CustomBlock(shape));
        drawingPanel.clear();

        if (drawnShapes.size() < 3) {
            drawingInstructions.setText("Shape " + (drawnShapes.size() + 1) + " of 3 — click cells, then click Done.");
        } else {
            cardLayout.show(cardPanel, "main");
            onThreeSelected(new ArrayList<>(drawnShapes));
            drawnShapes.clear();
        }
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
        if (currentStep < currentPlacements.size()) {
            currentStep++;
            renderStep(currentStep);
        }
    }

    private void renderStep(int step) {
        if (step < currentPlacements.size()) {
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

            instructionsArea.setText(
                    "Step " + (step + 1) + " of " + currentPlacements.size() + ":\n" +
                            "  Place " + current.block() + " at row " + current.row() + ", col " + current.col()
            );
            nextButton.setEnabled(true);
        } else {
            int[][] finalState = GridUtil.cloneGrid(this.grid);
            for (Placement p : currentPlacements) {
                BlockUtil.placeBlock(finalState, p.block().getShape(), p.row(), p.col());
                GridUtil.clearCompletedRow(finalState);
            }

            gridPanel.updateFromGrid(finalState);
            instructionsArea.setText("Round complete. Click Draw Custom Shapes for the next round.");
            nextButton.setEnabled(false);

            for (int r = 0; r < 8; r++) {
                System.arraycopy(finalState[r], 0, this.grid[r], 0, 8);
            }
            roundNumber++;
            setTitle("Block Blast Solver | Round - " + roundNumber);
        }
    }
}
