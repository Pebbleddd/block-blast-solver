package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.Block;

public class TwoByTwo extends Block {
    @Override
    public int[] getIndices(int startIndex) {
        return new int[]{
                startIndex,
                startIndex + 1,
                startIndex + 8,
                startIndex + 9,
        };
    }
}
