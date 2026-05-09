package dev.pebbled;

import dev.pebbled.gui.BlockBlastFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockBlastFrame::new);
    }
}
