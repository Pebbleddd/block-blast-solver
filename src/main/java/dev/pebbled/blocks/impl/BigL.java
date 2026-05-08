package dev.pebbled.blocks.impl;

import dev.pebbled.blocks.IBlock;
public class BigL implements IBlock {

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
        return "BIG L";
    }
}
