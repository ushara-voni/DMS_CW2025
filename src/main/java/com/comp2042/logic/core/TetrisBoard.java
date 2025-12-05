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


    /** Moves the current falling brick down by one row. */
    @Override public boolean moveBrickDown() { return brickManager.moveDown(); }
    /** Moves the current falling brick left by one column. */
    @Override public boolean moveBrickLeft() { return brickManager.moveLeft(); }

    /** Moves the current falling brick right by one column. */
    @Override public boolean moveBrickRight() { return brickManager.moveRight(); }
    /** Rotates the current falling brick counterclockwise. */
    @Override public boolean rotateLeftBrick() { return brickManager.rotate(); }

    /** Spawns a new falling brick. */
    @Override public boolean createNewBrick() { return brickManager.spawnNewBrick(); }


    /** Spawns a new falling brick. */
    @Override public int[][] getBoardMatrix() { return boardGrid.getMatrixCopy(); }
    /** Merges the falling brick into the board once it is placed. */
    @Override public void mergeBrickToBackground() { brickManager.mergeToBoard(); }
    /** Clears any full rows from the board and returns information about them. */
    @Override public ClearRow clearRows() { return boardGrid.clearRows(); }

    /** Gets the score tracker for the current game. */
    @Override public Score getScore() { return gameScore; }

    /** Retrieves the current falling brick's view data (position and shape). */
    @Override public ViewData getViewData() { return brickManager.getViewData(); }


    /** Resets the board, score, and spawns a new falling brick. */
    @Override
    public void newGame() {
        boardGrid.reset();
        gameScore.reset();
        brickManager.resetHold();
        brickManager.spawnNewBrick();
    }
    /** Holds the current falling brick and returns the new falling piece. */
    @Override
    public ViewData holdBrick() {
        return brickManager.holdPiece();
    }

    /** Returns the matrix of the currently held brick. */
    @Override
    public int[][] getHeldBrickMatrix() {
        return brickManager.getHeldBrickMatrix();
    }

}
