package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

public class CustomBlock implements IBlock {
    private final boolean[][] shape;

    public CustomBlock(boolean[][] shape) {
        this.shape = shape;
    }

    @Override
    public boolean[][] getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "CUSTOM";
    }
}
