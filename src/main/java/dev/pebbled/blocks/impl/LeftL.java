package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.Block;

public class LeftL extends Block {

    @Override
    public int[] getIndices(int startIndex) {
        return new int[]{
                startIndex,
                startIndex + 8,
                startIndex + 16,
                startIndex + 17,
        };
    }
}
