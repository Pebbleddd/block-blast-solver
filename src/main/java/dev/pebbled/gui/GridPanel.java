package dev.pebbled.gui;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private final JPanel[][] cells = new JPanel[8][8];

    public GridPanel() {
        setLayout(new GridLayout(8,8,1,1));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.DARK_GRAY);
                cell.setPreferredSize(new Dimension(50,50));
                add(cell);
                cells[row][col] = cell;
            }
        }
    }

    public void updateFromGrid(int[][] grid) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = cells[row][col];
                if (grid[row][col] == 0){
                    cell.setBackground(Color.DARK_GRAY);
                } else {
                    cell.setBackground(Color.MAGENTA);
                }
            }
        }
    }
}
