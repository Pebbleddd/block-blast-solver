package dev.pebbled.solver;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.grid.Grid;
import dev.pebbled.grid.Placement;
import dev.pebbled.utils.BlockUtil;
import dev.pebbled.utils.GridUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    public static Grid getTopGrid(int[][] grid, List<List<IBlock>> combinations) {
        LinkedHashSet<Grid> topGrids = new LinkedHashSet<>();

        for (List<IBlock> combination : combinations) {  // O(6)
            int[][] copyGrid = GridUtil.cloneGrid(grid);  // Make a new grid for each combination

            // gets each block in combination
            IBlock firstBlock = combination.getFirst();
            IBlock secondBlock = combination.get(1);
            IBlock thirdBlock = combination.getLast();

            LinkedHashSet<Grid> firstBlockBoards = getValidGrids(copyGrid, firstBlock);
            LinkedHashSet<Grid> secondBlockBoards = new LinkedHashSet<>();
            LinkedHashSet<Grid> thirdBlockBoards = new LinkedHashSet<>();

            for (Grid firstBlockBoard : firstBlockBoards) {
                secondBlockBoards.addAll(getMoreGrids(firstBlockBoard, secondBlock));
            }

            for (Grid secondBlockBoard : secondBlockBoards) {
                thirdBlockBoards.addAll(getMoreGrids(secondBlockBoard, thirdBlock));
            }

            LinkedHashSet<Grid> topGrid = thirdBlockBoards.stream().sorted(Comparator.comparingDouble(GridUtil::calculateScore).reversed()) // Sort by score descending
                    .limit(1).collect(Collectors.toCollection(LinkedHashSet::new));

            topGrids.addAll(topGrid);

        }

        LinkedHashSet<Grid> topGrid = topGrids.stream().sorted(Comparator.comparingDouble(GridUtil::calculateScore).reversed()) // Sort by score descending
                .limit(1).collect(Collectors.toCollection(LinkedHashSet::new));

        if (topGrid.isEmpty()) {
            return null;
        }

        return topGrid.getFirst();
    }

    // Gets all possible combinations for the first block
    private static LinkedHashSet<Grid> getValidGrids(int[][] grid, IBlock block) {
        int[][] copyGrid = GridUtil.cloneGrid(grid);

        LinkedHashSet<Grid> validGrids = new LinkedHashSet<>();

        for (int rowIndex = 0; rowIndex < copyGrid.length; rowIndex++) {  // O(8)
            for (int columnIndex = 0; columnIndex < copyGrid[rowIndex].length; columnIndex++) {  // O(8)
                int[][] copyOfCopy = GridUtil.cloneGrid(copyGrid);

                if (GridUtil.isSpaceEmpty(copyGrid, block.getShape(), rowIndex, columnIndex)) {
                    BlockUtil.placeBlock(copyOfCopy, block.getShape(), rowIndex, columnIndex);  // Places block on grid

                    int blocksCleared = GridUtil.clearCompletedRow(copyOfCopy);  // see how many blocks cleared as of result of placing that block
                    if (blocksCleared > 0) {
                        // notes from thinking
                        //TODO store combo increase for whole grid and then if the grid is accepted from the Grid.calcScore() calculate based on combo increase + linesCleared + clear space. but actually I need to  add that to the game session combo, at the start get the current combo from game session (maybe, might not even matter)
                        // just update calculations session combo based on if any lines were cleared and at the end from calcing score just check which grid increased the combo the most + had the highest line clears because I dont need the exact updating score. I just need a score at the end of 3 blocks
                    }
                    int linesCleared = blocksCleared / 8;
                    int score = (linesCleared * linesCleared);
                    Grid gridInstance = new Grid(copyOfCopy);

                    gridInstance.addClearedBlocks(blocksCleared);
                    gridInstance.setLinesClearedScore(score);
                    gridInstance.addGridToList(copyOfCopy);
                    gridInstance.addPlacement(new Placement(block, rowIndex, columnIndex));
                    validGrids.add(gridInstance);

                }

            }
        }
        return validGrids;
    }


    private static LinkedHashSet<Grid> getMoreGrids(Grid gridInstance, IBlock block) {
        int[][] copyGrid = GridUtil.cloneGrid(gridInstance.getGrid());

        LinkedHashSet<Grid> validGrids = new LinkedHashSet<>();

        for (int rowIndex = 0; rowIndex < copyGrid.length; rowIndex++) {  // O(8)
            for (int columnIndex = 0; columnIndex < copyGrid[rowIndex].length; columnIndex++) {  // O(8)

                int[][] copyOfCopy = GridUtil.cloneGrid(copyGrid);

                if (GridUtil.isSpaceEmpty(copyGrid, block.getShape(), rowIndex, columnIndex)) {
                    BlockUtil.placeBlock(copyOfCopy, block.getShape(), rowIndex, columnIndex);  // Places block on grid

                    Grid newGridInstance = new Grid(copyOfCopy);

                    int blocksCleared = GridUtil.clearCompletedRow(copyOfCopy);  // see how many blocks cleared as of result of placing that block
                    if (blocksCleared > 0) {
                        //TODO update
                    }
                    int linesCleared = blocksCleared / 8;
                    int score = (linesCleared * linesCleared);

                    newGridInstance.addClearedBlocks(blocksCleared + gridInstance.getClearedBlockCount());
                    newGridInstance.setLinesClearedScore(score + gridInstance.getLinesClearedScore());
                    newGridInstance.addGrid(copyOfCopy);

                    List<int[][]> blockGridList = gridInstance.getGridList();

                    blockGridList.add(copyOfCopy);

                    newGridInstance.addGrids(blockGridList);

                    newGridInstance.addPlacements(gridInstance.getGridBlockList());
                    newGridInstance.addPlacement(new Placement(block, rowIndex, columnIndex));

                    validGrids.add(newGridInstance);

                }

            }
        }
        return validGrids;
    }

    public static List<List<IBlock>> generateBlockCombinations(List<IBlock> blocks) {
        List<List<IBlock>> result = new ArrayList<>();
        if (blocks.size() == 1) {
            result.add(new ArrayList<>(blocks));  // Base case: only one combination (the list itself)
            return result;
        }

        // Loop through the list and recursively get combinations
        for (int i = 0; i < blocks.size(); i++) {
            IBlock currentBlock = blocks.get(i);
            List<IBlock> remainingBlocks = new ArrayList<>(blocks);
            remainingBlocks.remove(i);

            List<List<IBlock>> subCombinations = generateBlockCombinations(remainingBlocks);

            // Prepend current block to all sub-combinations
            for (List<IBlock> subPermutation : subCombinations) {
                List<IBlock> newPermutation = new ArrayList<>();
                newPermutation.add(currentBlock);
                newPermutation.addAll(subPermutation);
                result.add(newPermutation);
            }
        }

        return result;
    }
}
