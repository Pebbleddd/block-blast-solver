package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

import java.util.HashMap;
import java.util.Set;

public class LeftL implements IBlock {

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
        return "LeftL";
    }
}
