package com.comp2042.logic.core;

import com.comp2042.logic.bricks.MatrixOperations;
/**
 * Immutable class representing the state of a falling Tetris brick for rendering.
 * Includes the brick's matrix, its position on the board, and the next brick's matrix.
 */
public final class ViewData {
    /** The current brick's matrix data. */
    private final int[][] brickData;
    /** The x-coordinate (column) of the current brick. */
    private final int xPosition;
    /** The y-coordinate (row) of the current brick. */
    private final int yPosition;
    /** The matrix of the next brick to appear. */
    private final int[][] nextBrickData;


    /**
     * Constructs a ViewData instance.
     *
     * @param brickData matrix of the current falling brick
     * @param xPosition column position of the current brick
     * @param yPosition row position of the current brick
     * @param nextBrickData matrix of the next brick
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * Returns a copy of the current brick's matrix.
     *
     * @return 2D array representing the current brick
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Returns the x-coordinate (column) of the current brick.
     *
     * @return x-position
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Returns the y-coordinate (row) of the current brick.
     *
     * @return y-position
     */
    public int getyPosition() {
        return yPosition;
    }


    /**
     * Returns a copy of the next brick's matrix.
     *
     * @return 2D array representing the next brick
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
