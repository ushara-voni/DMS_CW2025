package com.comp2042.logic.core;

import com.comp2042.logic.bricks.BrickManager;

/**
 * Implementation of the Board interface for Tetris gameplay.
 * Manages the game board, falling bricks, held piece, and score.
 */
public class TetrisBoard implements Board {
    /** The grid representing locked blocks on the board. */
    private final BoardGrid boardGrid;
    /** Manager for the current falling brick and related operations. */
    private final BrickManager brickManager;

    /** Tracks the player's score. */
    private final Score gameScore;

    /**
     * Constructs a TetrisBoard with specified width and height.
     * Initializes the board, score, and spawns the first falling brick.
     *
     * @param width number of columns on the board
     * @param height number of rows on the board
     */
    public TetrisBoard(int width, int height) {
        boardGrid = new BoardGrid(width, height);
        brickManager = new BrickManager(boardGrid);
        gameScore = new Score();
        brickManager.spawnNewBrick();
    }


    /**
     * Attempts to move the current falling brick down by one row.
     *
     * @return {@code true} if the brick moved successfully, {@code false} if blocked
     */
    @Override public boolean moveBrickDown() { return brickManager.moveDown(); }
    /**
     * Attempts to move the current falling brick left by one column.
     *
     * @return {@code true} if the brick moved successfully, {@code false} if blocked
     */
    @Override public boolean moveBrickLeft() { return brickManager.moveLeft(); }

    /**
     * Attempts to move the current falling brick right by one column.
     *
     * @return {@code true} if the brick moved successfully, {@code false} if blocked
     */
    @Override public boolean moveBrickRight() { return brickManager.moveRight(); }
    /**
     * Attempts to move the current falling brick clockwise.
     *
     * @return {@code true} if the brick moved successfully, {@code false} if blocked
     */
    @Override public boolean rotateLeftBrick() { return brickManager.rotate(); }

    /**
     * Spawns a new falling brick from the queue.
     *
     * @return {@code true} if the new brick was successfully spawned, {@code false} if it collides (game over)
     */
    @Override public boolean createNewBrick() { return brickManager.spawnNewBrick(); }


    /**
     * Returns a copy of the current board matrix, including locked blocks.
     *
     * @return a 2D integer array representing the board
     */
    @Override public int[][] getBoardMatrix() { return boardGrid.getMatrixCopy(); }

    /**
     * Merges the current falling brick into the board grid permanently.
     */
    @Override public void mergeBrickToBackground() { brickManager.mergeToBoard(); }
    /**
     * Checks for and removes any full rows from the board.
     *
     * @return {@link ClearRow} object containing cleared row count, updated board, and score bonus
     */
    @Override public ClearRow clearRows() { return boardGrid.clearRows(); }

    /**
     * Returns the score tracker for the current game.
     *
     * @return the {@link Score} instance
     */
    @Override public Score getScore() { return gameScore; }

    /**
     * Retrieves the current falling brick's view information, including
     * its shape, position, and the next piece preview.
     *
     * @return {@link ViewData} object representing the current brick
     */
    @Override public ViewData getViewData() { return brickManager.getViewData(); }


    /** Resets the board, score, and spawns a new falling brick. */
    @Override
    public void newGame() {
        boardGrid.reset();
        gameScore.reset();
        brickManager.resetHold();
        brickManager.spawnNewBrick();
    }
    /**
     * Holds the current falling brick and spawns the next brick if needed.
     *
     * @return {@link ViewData} for the new falling brick
     */
    @Override
    public ViewData holdBrick() {
        return brickManager.holdPiece();
    }

    /**
     * Returns the matrix of the currently held brick, or {@code null} if no brick is held.
     *
     * @return a 2D integer array representing the held brick
     */
    @Override
    public int[][] getHeldBrickMatrix() {
        return brickManager.getHeldBrickMatrix();
    }

}
