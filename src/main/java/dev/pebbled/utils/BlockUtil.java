package dev.pebbled.utils;

public class BlockUtil {
    public static void addBlock(int[] table, int[] indices) {
        for (int index : indices) {
            table[index] = 1;
        }
    }
}
