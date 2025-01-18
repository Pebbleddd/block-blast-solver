package dev.pebbled.utils;

public class BlockUtil {
    //TODO Maybe change this to implement HashMap<Integer, Set<Integer>> from getIndices
    public static void placeBlock(int[][] grid, int[][] block, int startRow, int startCol) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                grid[startRow + i][startCol + j] = block[i][j];
            }
        }
    }
}
