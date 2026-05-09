package dev.pebbled.gui;

import dev.pebbled.blocks.IBlock;
import dev.pebbled.blocks.impl.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PalettePanel extends JPanel {
    private static final List<IBlock> AVAILABLE_BLOCKS = List.of(
            new L(), new ReverseL(), new BigL(), new TwoByTwo(), new UpsideDownSmallT()
    );

    private final List<IBlock> selected = new ArrayList<>();

    private final Consumer<List<IBlock>> onThreeSelected;

    public PalettePanel(Consumer<List<IBlock>> onThreeSelected) {
        this.onThreeSelected = onThreeSelected;

        setLayout(new GridLayout(1, 5, 5, 5));

        for (IBlock block : AVAILABLE_BLOCKS) {
            BlockPanel blockPanel = new BlockPanel(block, this::handleBlockClick);
            add(blockPanel);
        }
    }

    private void handleBlockClick(IBlock block) {
        selected.add(block);
        System.out.println("Selected: " + block + " (" + selected.size() + "/3)");
        if (selected.size() == 3) {
            onThreeSelected.accept(new ArrayList<>(selected));   // pass a COPY
            selected.clear();
        }
    }
}
