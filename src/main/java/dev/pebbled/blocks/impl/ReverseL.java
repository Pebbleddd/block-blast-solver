package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

public class ReverseL implements IBlock {

    @Override
    public boolean[][] getShape() {
        return new boolean[][] {
                {false,  true},
                {false,  true},
                {true,  true },
        };
    }

    @Override
    public String toString() {
        return "REVERSE L";
    }
}
