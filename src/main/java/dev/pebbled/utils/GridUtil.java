package dev.pebbled.utils;

import dev.pebbled.algorithm.FloodFill;
import dev.pebbled.grid.Grid;

import java.util.*;

public class GridUtil {

    private static final int[] FULL_ROW = {1, 1, 1, 1, 1, 1, 1, 1};

    public static boolean isSpaceEmpty(int[][] grid, boolean[][] shape, int rowAnchor, int columnAnchor) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    if (i + rowAnchor < 0 || i + rowAnchor >= grid.length || j + columnAnchor < 0 || j + columnAnchor >= grid[0].length) {
                        return false;
                    }
                    if (grid[i + rowAnchor][j + columnAnchor] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int clearCompletedRow(int[][] grid) {
        HashMap<Integer, Set<Integer>> indices = new HashMap<>();

        // Find completed rows
        for (int row = 0; row < grid.length; row++) {
            indices.put(row, new HashSet<>());
            if (Arrays.equals(grid[row], FULL_ROW)) {
                for (int i = 0; i < grid.length; i++) {
                    indices.get(row).add(i);
                }
            }
        }

        // Find completed columns
        for (int col = 0; col < grid[0].length; col++) {
            int[] column = new int[grid.length];

            for (int row = 0; row < grid.length; row++) {
                column[row] = grid[row][col];
            }

            if (Arrays.equals(column, FULL_ROW)) {
                for (int i = 0; i < grid.length; i++) {
                    indices.get(i).add(col);
                }
            }
        }

        // Remove blocks from found indices
        int removed = 0;

        for (Map.Entry<Integer, Set<Integer>> entry : indices.entrySet()) {
            int rowIndex = entry.getKey();

            for (int index : entry.getValue()) {
                grid[rowIndex][index] = 0;
                removed++;
            }
        }

        return removed;
    }

    public static float calculateScore(Grid gridInstance) {

        int[][] grid = gridInstance.getGrid();

        int blocksCleared = gridInstance.getClearedBlockCount();

        float largestArea = (float) (largestArea(grid) * 0.25);
        float clearedArea = (float) (blocksCleared * 0.5);  // Higher weight for blocks broken

        return largestArea + clearedArea;
    }

    public static int largestArea(int[][] grid) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int maxArea = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0 && !visited[row][col]) { // If ⬛ and not visited
                    int area = FloodFill.floodFill(grid, visited, row, col);
                    maxArea = Math.max(maxArea, area); // Track the largest area
                }
            }
        }
        return maxArea;
    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print((String.valueOf(cell).equals("0")) ? "⬛" : "🟪");
            }
            System.out.println(); // New line for each row
        }
    }

    public static int[][] cloneGrid(int[][] grid) {
        int[][] copyGrid = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copyGrid[i] = grid[i].clone(); // Clones each inner array
        }
        return copyGrid;
    }
}