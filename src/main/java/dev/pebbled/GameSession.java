package dev.pebbled;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.blocks.impl.*;
import dev.pebbled.grid.Grid;
import dev.pebbled.solver.Solver;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.GridUtil;

import java.util.List;
import java.util.Scanner;

public class GameSession {
    private final int[][] grid;
    private final Scanner scanner;

    private static final List<IBlock> AVAILABLE_BLOCKS = List.of(
            new L(),
            new ReverseL(),
            new BigL(),
            new TwoByTwo(),
            new UpsideDownSmallT()
    );

    public GameSession() {
        grid = new int[8][8];
        scanner = new Scanner(System.in);
    }

    public void run() {
        printIntro();
        while (true) {
            List<IBlock> blocks = selectBlocks();
            if (blocks == null) {
                break;
            }
            Grid result = solve(blocks);
            if (result == null) {
                System.out.println("No solution found for the given blocks.");
                break;
            }
            displayResult(result);
            updateGrid(result);
        }
        printExitMessage();
    }

    private void printIntro() {
        System.out.println("==================");
        System.out.println("Welcome to Block Blast Solver!");
        System.out.println("Coded by Pebbled");
        System.out.println("https://github.com/Pebbled/BlockBlastSolver");
        System.out.println("==================");
        GridUtil.printGrid(grid);
        System.out.println("==================");
        System.out.println("Enter your three blocks each round, or type 'quit' to exit.");
    }

    private List<IBlock> selectBlocks() {
        List<IBlock> selected = new java.util.ArrayList<>();
        for (int i = 0; i < AVAILABLE_BLOCKS.size(); i++) {
            System.out.println((i + 1) + ": " + AVAILABLE_BLOCKS.get(i));
        }
        while (selected.size() < 3) {
            System.out.println("Block " + (selected.size() + 1) + " of 3: " );
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) {
                return null;
            }
            try {
                int blockNumber = Integer.parseInt(input);
                if (blockNumber > 0 && blockNumber <= AVAILABLE_BLOCKS.size()) {
                    selected.add(AVAILABLE_BLOCKS.get(blockNumber - 1));
                } else {
                    System.out.println("Invalid block number. Please enter a number between 1 and " + AVAILABLE_BLOCKS.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid block number.");
            }
        }
        return selected;
    }

    private Grid solve(List<IBlock> blocks) {
        List<List<IBlock>> combinations = Solver.generateBlockCombinations(blocks);
        return Solver.getTopGrid(this.grid, combinations);
    }

    private void displayResult(Grid topGrid) {
        System.out.println("=== Optimal placement ===");
        System.out.println("Score: " + GridUtil.calculateScore(topGrid));
        System.out.println("=========================");
        System.out.println("=== Final Grid ===");
        GridUtil.printGrid(topGrid.getGrid());

        topGrid.getGridBlockList().forEach(placement -> {
            System.out.println("Place " + placement.block() + " at row " + (placement.row() + 1) + ", col " + (placement.col() + 1));
            int[][] cloneGrid = GridUtil.cloneGrid(this.grid);
            BlockUtil.placeBlock(cloneGrid, placement.block().getShape(), placement.row(), placement.col());
            GridUtil.printGrid(cloneGrid);
        });
    }

    private void updateGrid(Grid topGrid) {
        int[][] src = topGrid.getGrid();
        for (int r = 0; r < this.grid.length; r++) {
            System.arraycopy(src[r], 0, this.grid[r], 0, this.grid[r].length);
        }
    }

    private void printExitMessage() {
        System.out.println("Thank you for using Block Blast Solver!");
    }
}
