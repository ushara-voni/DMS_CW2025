package com.comp2042.logic.bricks;

import com.comp2042.logic.core.ClearRow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing various matrix operations used in Tetris gameplay,
 * including collision checking, merging bricks into the board, line clearing,
 * and deep-copying matrix data structures.
 *
 * This class is non-instantiable.
 */

public class MatrixOperations {


    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MatrixOperations(){

    }

    /**
     * Checks whether the given brick matrix intersects with the board matrix at the
     * specified (x, y) position. An intersection occurs if any non-zero brick cell:
     * <ul>
     *     <li>is out of bounds of the board matrix, or</li>
     *     <li>collides with a non-zero cell already on the board</li>
     * </ul>
     *
     * @param matrix the board matrix representing placed blocks
     * @param brick the current falling brick matrix
     * @param x horizontal position of the brick's top-left corner on the board
     * @param y vertical position of the brick's top-left corner on the board
     * @return {@code true} if an intersection or boundary issue occurs, otherwise {@code false}
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the given coordinates are outside the bounds of the board matrix.
     *
     * @param matrix the board matrix
     * @param targetX x-coordinate to check
     * @param targetY y-coordinate to check
     * @return {@code true} if the position is out of bounds, otherwise {@code false}
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Creates a deep copy of a 2D integer matrix.
     *
     * @param original the matrix to copy
     * @return new 2D array with duplicated values
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }


    /**
     * Merges the given brick matrix into the board matrix at the provided coordinates.
     * Only non-zero brick cells overwrite the board's cells.
     *
     * @param filledFields the current board matrix
     * @param brick the brick matrix to merge
     * @param x horizontal merge position
     * @param y vertical merge position
     * @return new matrix representing the board after the brick has been placed
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }


    /**
     * Checks for completed rows in the board matrix. Fully filled rows are removed,
     * and remaining rows are shifted down. A score bonus is calculated based on
     * number of cleared rows.
     *
     * @param matrix the current board state
     * @return {@link ClearRow} object containing the number of cleared rows,
     *         updated matrix, and awarded score
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Creates a deep copy of a list of 2D int matrices.
     *
     * @param list a list of matrices to duplicate
     * @return a new list where each matrix element is deeply copied
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
