package dev.pebbled.utils;

import java.util.Arrays;
import java.util.HashSet;

public class BoardUtil {
    private final static int TABLE_SIZE = 63;
    private final static int TABLE_VERTICAL_SIZE = 56;
    private final static int COLUMN_VERTEX = 8;

    public static boolean containsBlockAtIndices(int[] table, int[] indices) {
        for (int index : indices) {
            try {
                if (table[index] == 1) {
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
                return true;
            }
        }
        return false;
    }

    public static HashSet<Integer> checkForConnection(int[] table) {
        HashSet<Integer> indicesToRemove = new HashSet<>();

        for (int firstColumnIndex = 0; firstColumnIndex < COLUMN_VERTEX; firstColumnIndex++) {  // loops first row
            boolean connected = true;

            for (int columnIndex = firstColumnIndex; columnIndex <= TABLE_SIZE; columnIndex += COLUMN_VERTEX) {  // loops columns
                if (table[columnIndex] == 0) {
                    connected = false;
                    break;
                }
            }

            if (connected) { // if the column has uninterrupted 1's
                for (int columnIndex = firstColumnIndex; columnIndex <= TABLE_SIZE; columnIndex += COLUMN_VERTEX) {
                    table[columnIndex] = 0;
                    indicesToRemove.add(columnIndex);
                }
            }

        }

        for (int firstRowIndex = 0; firstRowIndex <= TABLE_VERTICAL_SIZE; firstRowIndex += COLUMN_VERTEX) {  // loop first column
            boolean connected = true;

            for (int rowIndex = firstRowIndex; rowIndex < firstRowIndex + COLUMN_VERTEX ; rowIndex++) {  // loops rows
                if (table[rowIndex] == 0) {
                    connected = false;
                    break;
                }
            }

            if (connected) {// if the row has uninterrupted 1's
                for (int rowIndex = firstRowIndex; rowIndex < firstRowIndex + COLUMN_VERTEX; rowIndex++) {
                    //table[rowIndex] = 0;
                    indicesToRemove.add(rowIndex);
                }
            }

        }
        //indicesToRemove.forEach(integer -> table[integer] = 0);  // removes blocks from table
        return indicesToRemove;
    }

    public static void printTable(int[] table) {
        for (int i = 0; i < table.length; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.print(" ");
            System.out.print(table[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void fillTable(int[] table) {
        Arrays.fill(table, 1);
    }

}
