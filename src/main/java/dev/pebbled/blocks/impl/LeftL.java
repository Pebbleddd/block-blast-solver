package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.Block;

import java.util.HashMap;
import java.util.Set;

public class LeftL extends Block {

    @Override
    public int[] getIndices(int startIndex) {
        return new int[] {
                startIndex,
                startIndex + 8,
                startIndex + 16,
                startIndex + 17,
        };
    }

    public int[][] getIndices2D(int rowPos, int columnPos) {
        return new int[][] {
                {rowPos, columnPos},
                {rowPos + 1, columnPos},
                {rowPos + 2, columnPos},
                {rowPos + 2, columnPos + 1}
        };
    }

    public HashMap<Integer, Set<Integer>> getIndices(int rowPosition, int columnPosition) {
        HashMap<Integer, Set<Integer>> hashMap = new HashMap<>();
        hashMap.put(rowPosition, Set.of(columnPosition));
        hashMap.put(rowPosition + 1, Set.of(columnPosition));
        hashMap.put(rowPosition + 2, Set.of(columnPosition, columnPosition + 1));
        return hashMap;
    }
}
