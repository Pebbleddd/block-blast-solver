package dev.pebbled.gui;

import dev.pebbled.blocks.IBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class BlockPanel extends JPanel {
    public BlockPanel(IBlock block, Consumer<IBlock> onClick) {
        boolean[][] shape = block.getShape();

        setLayout(new GridLayout(shape.length, shape[0].length,1,1));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        for (boolean[] booleans : shape) {
            for (int col = 0; col < shape[0].length; col++) {
                JPanel cell = new JPanel();

                cell.setBackground(booleans[col] ? Color.MAGENTA : Color.DARK_GRAY);
                cell.setPreferredSize(new Dimension(20, 20));
                add(cell);
            }
        }
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.accept(block);
            }
        };
        addMouseListener(listener);
        for (Component c : getComponents()) {
            c.addMouseListener(listener);
        }
    }
}
