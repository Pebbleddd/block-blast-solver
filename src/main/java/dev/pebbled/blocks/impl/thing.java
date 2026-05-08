package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;


public class thing implements IBlock {


    @Override
    public boolean[][] getShape() {
        return new boolean[][] {
                {false,  true, false},
                {true,  true, true}
        };
    }

    @Override
    public String toString() {
        return "upthing";
    }
}
