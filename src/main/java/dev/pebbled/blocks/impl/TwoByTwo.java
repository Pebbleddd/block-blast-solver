package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

public class TwoByTwo implements IBlock {

    @Override
    public boolean[][] getShape() {
        return new boolean[][] {
                {true,  true},
                {true,  true},
        };
    }

    @Override
    public String toString() {
        return "TWO BY TWO";
    }
}
