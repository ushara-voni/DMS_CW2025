package com.comp2042.logic.core;

import com.comp2042.logic.bricks.MatrixOperations;
/**
 * Represents the result of clearing rows from the Tetris board.
 * Contains information about the number of lines removed, the updated board matrix,
 * and the score bonus earned from the cleared lines.
 */
public final class ClearRow {

    /** Number of lines cleared from the board. */
    private final int linesRemoved;

    /** The new board matrix after clearing lines. */
    private final int[][] newMatrix;

    /** Score bonus awarded for the cleared lines. */
    private final int scoreBonus;

    /**
     * Constructs a ClearRow result object.
     *
     * @param linesRemoved number of lines cleared
     * @param newMatrix the updated board matrix after removal
     * @param scoreBonus points awarded for the cleared lines
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of lines removed.
     *
     * @return number of cleared rows
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Returns a copy of the updated board matrix after row clearing.
     *
     * @return new board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus awarded for clearing rows.
     *
     * @return score bonus points
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
