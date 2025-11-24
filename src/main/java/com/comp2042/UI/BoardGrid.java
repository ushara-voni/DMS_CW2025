package com.comp2042.UI;

import com.comp2042.brick_functions.MatrixOperations;

public class BoardGrid {

    private final int width;
    private final int height;
    private int[][] matrix;

    public BoardGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[width][height]; // [row][col]
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /** Returns a copy to prevent external modification */
    public int[][] getMatrixCopy() {
        int[][] copy = new int[width][height];
        for (int i = 0; i < width; i++)
            System.arraycopy(matrix[i], 0, copy[i], 0, height);
        return copy;
    }

    /** Merge shape into the board matrix */
    public void merge(int[][] shape, int x, int y) {
        matrix = MatrixOperations.merge(matrix, shape, x, y);
    }

    /** Clear full rows and return info */
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(matrix);
        matrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /** Reset the board */
    public void reset() {
        matrix = new int[width][height];
    }
}
