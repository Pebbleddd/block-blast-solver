package dev.pebbled.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class DrawingPanel extends JPanel {
    private final boolean[][] state = new boolean[8][8];
    private final JPanel[][] cells = new JPanel[8][8];

    public DrawingPanel() {
        setLayout(new GridLayout(8,8,1,1));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.DARK_GRAY);
                cell.setPreferredSize(new Dimension(50,50));

                final int r = row;
                final int c = col;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        toggleCell(r, c);
                    }
                });
                cells[row][col] = cell;
                add(cell);
            }
        }
    }

    private void toggleCell(int row, int col) {
        state[row][col] = !state[row][col];
        cells[row][col].setBackground(state[row][col] ? Color.MAGENTA : Color.DARK_GRAY);
    }

    public boolean[][] getShape() {
        int minRow = 8;
        int maxRow = -1;
        int minCol = 8;
        int maxCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (state[row][col]) {
                    minRow = Math.min(minRow, row);
                    maxRow = Math.max(maxRow, row);
                    minCol = Math.min(minCol, col);
                    maxCol = Math.max(maxCol, col);
                }
            }
        }

        if (maxRow == -1) {
            return null;
        }

        int height = maxRow - minRow + 1;
        int width = maxCol - minCol + 1;
        boolean[][] shape = new boolean[height][width];
        for (int row = minRow; row <= maxRow; row++) {
            if (maxCol + 1 - minCol >= 0)
                System.arraycopy(state[row], minCol, shape[row - minRow], 0, maxCol + 1 - minCol);
        }
        return shape;
    }

    public void clear() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                state[row][col] = false;
                cells[row][col].setBackground(Color.DARK_GRAY);
            }
        }
    }
}
