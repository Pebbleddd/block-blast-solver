package dev.pebbled;

import dev.pebbled.blocks.Block;
import dev.pebbled.blocks.impl.LeftL;
import dev.pebbled.blocks.impl.RightL;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.BoardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final int[] table = new int[64];

    public static void main(String[] args) {
        BoardUtil.printTable(table);
        //BoardUtil.fillTable(table);
        //BoardUtil.printTable(table);
//        BlockUtil.addBlock(table, new LeftL().getIndices(19));
//        BlockUtil.addBlock(table, new RightL().getIndices(14));

        calculateMoves(new LeftL(), new LeftL(), new LeftL());
        BoardUtil.printTable(table);
    }

    private static void calculateMoves(Block a, Block b, Block c) {
        int[] copyTable = table;
        List<Block> blocks = List.of(a, b, c);

        // Generate all combinations
        List<List<Block>> combinations = generateBlockCombinations(blocks);  // list of a list
        validBlockPlacements(table, combinations);
//        for (List<Block> combination : combinations) {
//            combination.forEach(block -> {
//                for (int i = 0; i < copyTable.length; i++) {  // loops each space in table
//                    if (BoardUtil.containsOneAtIndices(copyTable, block.getIndices(i))) {
//                        continue;
//                    }
//                    BlockUtil.addBlock(copyTable, block.getIndices(i));
//                }
//            });
//        }
//
//
//
//
//
//        // Pass each permutation to a method
//        for (List<Block> combination : combinations) {  // loop block combinations
//            for (Block block : combination) {  // loop each block in combination
//                for (int index = 0; index < copyTable.length; index++) {  // loop each spot in table
//                    if (BoardUtil.containsOneAtIndices(copyTable, block.getIndices(index))) {  // if space is available to place block
//                        continue;
//                    }
//                    BlockUtil.addBlock(copyTable, block.getIndices(index));
//                }
//            }
//        }

    }


    private static void validBlockPlacements(int[] table, List<List<Block>> combinations) {
        List<List<List<Integer>>> validPlacements = new ArrayList<>();
        List<Block> validCombination = new ArrayList<>();
        List<List<int[]>> validIndices = new ArrayList<>();



        combinations.forEach(combination -> {
                for (int index = 0; index < table.length; index++) {  // loop each spot in copyTable
                    int[] copyTable = table;

                    int[] firstBlockIndices = combination.getFirst().getIndices(index);
                    int[] secondBlockIndices = combination.get(1).getIndices(index);
                    int[] lastBlockIndices = combination.getLast().getIndices(index);
//                    validIndices.add(List.of(firstBlockIndices));
//                    validIndices.add(List.of(secondBlockIndices));
//                    validIndices.add(List.of(lastBlockIndices));
                    if (BoardUtil.containsBlockAtIndices(copyTable, firstBlockIndices)) {  // if space is NOT available to place block
                        continue;
                    }

                    BlockUtil.addBlock(copyTable, firstBlockIndices);
                    BoardUtil.checkForConnection(copyTable);
                    System.out.println("Past 1st block");

                    if (BoardUtil.containsBlockAtIndices(copyTable, secondBlockIndices)) {
                        continue;
                    }

                    BlockUtil.addBlock(copyTable, secondBlockIndices);
                    BoardUtil.checkForConnection(copyTable);
                    System.out.println("Past 2nd block");

                    if (BoardUtil.containsBlockAtIndices(copyTable, lastBlockIndices)) {
                        continue;
                    }
                    System.out.println("Past 3rd block");
                    validCombination.addAll(List.of(combination.getFirst(), combination.get(1), combination.getLast()));  // add a valid combo

                    validIndices.add(List.of(firstBlockIndices));
                    validIndices.add(List.of(secondBlockIndices));
                    validIndices.add(List.of(lastBlockIndices));

                }
        });
        validIndices.forEach(ints -> ints.forEach(ints1 -> System.out.println(ints1.length)));
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
