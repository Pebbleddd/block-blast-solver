package dev.pebbled.utils;

import java.util.*;

public class GridUtil {

    private static final int[] FULL_ROW = {1, 1, 1, 1, 1, 1, 1, 1};

    public static boolean isSpaceEmpty(int[][] grid, HashMap<Integer, Set<Integer>> indices) {
        for (Map.Entry<Integer, Set<Integer>> entry : indices.entrySet()) {
            int rowIndex = entry.getKey();

            for (int index : entry.getValue()) {
                if (rowIndex < 0 || rowIndex >= grid.length || index < 0 || index >= grid[rowIndex].length) {
                    return false;
                }
                if (grid[rowIndex][index] == 1) {
                    return false;
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

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print((String.valueOf(cell).equals("0")) ? "⬛" : "🟪");
            }
            System.out.println(); // New line for each row
        }
    }
}