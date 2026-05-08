package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

public class L implements IBlock {

    @Override
    public boolean[][] getShape() {
        return new boolean[][] {
                {true,  false},
                {true,  false},
                {true,  true },
        };
    }

    @Override
    public String toString() {
        return "L";
    }
}
