package dev.pebbled;

import dev.pebbled.blocks.Block;
import dev.pebbled.blocks.impl.LeftL;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.GridUtil;

import java.util.*;

public class Main {

    private static int[][] grid = new int[8][8];
    public static void main(String[] args) {

        // Fill Row
        grid[0][0] = 1;
        grid[0][1] = 1;
        grid[0][2] = 1;
        grid[0][3] = 1;
        grid[0][4] = 1;
        grid[0][5] = 1;
        grid[0][6] = 1;
        grid[0][7] = 1;

        // Fill Column
        grid[0][1] = 1;
        grid[1][1] = 1;
        grid[2][1] = 1;
        grid[3][1] = 1;
        grid[4][1] = 1;
        grid[5][1] = 1;
        grid[6][1] = 1;
        grid[7][1] = 1;

        GridUtil.printGrid(grid);
        System.out.println();
        System.out.println();
        int removedBlocks = GridUtil.clearCompletedRow(grid);
        GridUtil.printGrid(grid);
        System.out.println("removedBlocks = " + removedBlocks);

    }

    private static void calculateMoves(Block a, Block b, Block c) {
        List<Block> blocks = List.of(a, b, c);

        // Generate all combinations
        List<List<Block>> combinations = generateBlockCombinations(blocks);  // list of a list
        //validBlockPlacements(combinations);


    }


    private static List<List<Block>> generateBlockCombinations(List<Block> blocks) {
        List<List<Block>> result = new ArrayList<>();
        if (blocks.size() == 1) {
            result.add(new ArrayList<>(blocks));  // Base case: only one combination (the list itself)
            return result;
        }

        // Loop through the list and recursively get combinations
        for (int i = 0; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            List<Block> remainingBlocks = new ArrayList<>(blocks);
            remainingBlocks.remove(i);

            List<List<Block>> subCombinations = generateBlockCombinations(remainingBlocks);

            // Prepend current block to each sub-combinations
            for (List<Block> subPermutation : subCombinations) {
                List<Block> newPermutation = new ArrayList<>();
                newPermutation.add(currentBlock);
                newPermutation.addAll(subPermutation);
                result.add(newPermutation);
            }
        }

        return result;
    }
}
