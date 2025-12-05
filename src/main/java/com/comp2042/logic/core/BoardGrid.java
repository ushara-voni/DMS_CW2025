package com.comp2042.logic.core;

import com.comp2042.logic.bricks.MatrixOperations;

/**
 * Represents the game board grid used in Tetris gameplay.
 * The board stores a 2D integer matrix where each entry indicates whether
 * a cell is empty or filled with a brick segment. This class provides utilities
 * for inspecting the grid, merging falling bricks into the board, clearing
 * completed rows, and resetting the grid when needed.
 */
public class BoardGrid {

    /** The width of the board in columns. */
    private final int width;   // columns

    /** The height of the board in rows. */
    private final int height;  // rows

    /** The underlying matrix storing placed brick cells. */
    private int[][] matrix;

    /**
     * Creates a new board grid with the specified width and height.
     *
     * @param width  number of columns in the board
     * @param height number of rows in the board
     */
    public BoardGrid(int width, int height) {
        this.width = width;    // 15
        this.height = height;  // 33
        this.matrix = new int[height][width];  // 33 rows, 15 cols
    }


    /**
     * Returns a deep copy of the board matrix.
     * This is used to safely perform collision detection without modifying
     * the actual grid.
     *
     * @return a copied 2D array representing the current board state
     */
    public int[][] getMatrixCopy() {
        int[][] copy = new int[height][width];
        for (int r = 0; r < height; r++)
            System.arraycopy(matrix[r], 0, copy[r], 0, width);
        return copy;
    }


    /**
     * Resets the board by clearing all filled cells.
     * This creates a fresh empty matrix of the same size.
     */
    public void reset() {
        matrix = new int[height][width];
    }
    /**
     * Checks for and clears any completed rows in the board.
     * Delegates the row-checking logic to {@link MatrixOperations#checkRemoving(int[][])}.
     * After clearing rows, the internal matrix is updated with the returned result.
     *
     * @return a {@link ClearRow} object containing information about the cleared rows
     */
    public ClearRow clearRows() { ClearRow clearRow = MatrixOperations.checkRemoving(matrix); matrix = clearRow.getNewMatrix(); return clearRow; }


    /**
     * Merges a falling tetromino shape into the board at the given coordinates.
     * This operation permanently embeds the shape into the board grid.
     * Uses {@link MatrixOperations#merge(int[][], int[][], int, int)} to
     * handle bounds and data merging.
     *
     * @param shape the shape matrix to merge into the board
     * @param x     the x-coordinate (column) at which to place the shape
     * @param y     the y-coordinate (row) at which to place the shape
     */
    public void merge(int[][] shape, int x, int y) { matrix = MatrixOperations.merge(matrix, shape, x, y); }
}
