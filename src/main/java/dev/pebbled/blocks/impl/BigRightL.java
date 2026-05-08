package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;

import java.util.HashMap;
import java.util.Set;

public class BigRightL implements IBlock {

   @Override
   public boolean[][] getShape() {
       return new boolean[][] {
               {true,  false, false},
               {true,  false, false},
               {true,  true, true },
       };
   }

    @Override
    public String toString() {
        return "BigRightL";
    }
}
