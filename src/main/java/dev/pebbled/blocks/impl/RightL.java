package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

import java.util.HashMap;
import java.util.Set;

public class RightL implements IBlock {

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
        return "right_l";
    }
}
