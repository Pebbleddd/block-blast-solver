package dev.pebbled.grid;

import dev.pebbled.utils.GridUtil;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private int[][] grid;

    private final List<Placement> placements = new ArrayList<>();

    private final List<int[][]> blockGridList = new ArrayList<>();

    private int clearedBlockCount = 0;

    private int linesClearedScore = 0;

    public Grid(int[][] grid) {
        this.grid = GridUtil.cloneGrid(grid);
    }

    public void addGrid(int[][] grid) {
        this.grid = GridUtil.cloneGrid(grid);
    }

    public void addGrids(List<int[][]> grid) {
        this.blockGridList.addAll(grid);
    }

    public void addGridToList(int[][] grid) {
        this.blockGridList.add(grid);
    }

    public void addPlacement(Placement placement) {
        this.placements.add(placement);
    }

    public void addPlacements(List<Placement> placements) {
        this.placements.addAll(placements);
    }

    public int getClearedBlockCount() {
        return clearedBlockCount;
    }

    public void addClearedBlocks(int removedBlocks) {
        clearedBlockCount += removedBlocks;
    }

    public int getLinesClearedScore() {
        return linesClearedScore;
    }

    public void setLinesClearedScore(int linesClearedScore) {
        this.linesClearedScore += linesClearedScore;
    }

    public int[][] getGrid() {
        return grid;
    }

    public List<int[][]> getGridList() {
        return blockGridList;
    }

    public List<Placement> getGridBlockList() {
        return this.placements;
    }
}
