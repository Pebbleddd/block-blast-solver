package dev.pebbled.grid;

import dev.pebbled.blocks.IBlock;

public record Placement(IBlock block, int row, int col) {}

