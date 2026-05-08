package dev.pebbled.utils;

public class BlockUtil {

    public static void placeBlock(int[][] grid, boolean[][] shape, int rowAnchor, int columnAnchor) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    grid[i + rowAnchor][j + columnAnchor] = 1;
                }
            }
        }
    }
}
