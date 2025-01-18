package dev.pebbled.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockUtil {

    public static void placeBlock(int[][] grid, HashMap<Integer, Set<Integer>> indices) {
        try {
            for (Map.Entry<Integer, Set<Integer>> entry : indices.entrySet()) {
                int rowIndex = entry.getKey();
                for (int index : entry.getValue()) {
                    grid[rowIndex][index] = 1;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("This shouldn't of happened?");
        }
    }

}
