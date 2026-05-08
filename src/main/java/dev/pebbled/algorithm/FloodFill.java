package dev.pebbled.algorithm;

public class FloodFill {
    public static int floodFill(int[][] grid, boolean[][] visited, int row, int col) {
        // Base cases
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return 0;
        if (grid[row][col] == 1 || visited[row][col]) return 0; // Wall or visited

        visited[row][col] = true; // Mark as visited
        int area = 1; // Count current cell

        // Check all 4 directions
        area += floodFill(grid, visited, row + 1, col); // Down
        area += floodFill(grid, visited, row - 1, col); // Up
        area += floodFill(grid, visited, row, col + 1); // Right
        area += floodFill(grid, visited, row, col - 1); // Left

        return area;
    }
}
