package com.comp2042.logic.core;

/**
 * Represents the game board operations and state retrieval
 * required for Tetris gameplay control and rendering.
 */
public interface Board {

    /**
     * Moves the current falling brick downward by one row.
     *
     * @return true if the move is successful, false if a collision prevents it
     */
    boolean moveBrickDown();

    /**
     * Moves the current falling brick one column to the left.
     *
     * @return true if the move is valid, otherwise false
     */
    boolean moveBrickLeft();

    /**
     * Moves the current falling brick one column to the right.
     *
     * @return true if the move is valid, otherwise false
     */
    boolean moveBrickRight();


    /**
     * Rotates the current falling brick counterclockwise if possible.
     *
     * @return true if the rotation occurs successfully, otherwise false
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new falling brick when the current one is locked.
     *
     * @return true if creation is successful, false if game over
     */
    boolean createNewBrick();

    /**
     * Retrieves the entire game board matrix including all locked blocks.
     *
     * @return 2D array representing board state
     */
    int[][] getBoardMatrix();

    /**
     * Retrieves the current view data representing the falling brick's position and shape.
     *
     * @return ViewData for the active Tetromino
     */
    ViewData getViewData();


    /**
     * Merges the current falling brick into the board once placed.
     */
    void mergeBrickToBackground();

    /**
     * Checks for full rows and clears them if present.
     *
     * @return ClearRow information about cleared rows and scoring
     */
    ClearRow clearRows();

    /**
     * Gets the score system associated with the board.
     *
     * @return Score object tracking gameplay score
     */
    Score getScore();
    /**
     * Resets the board and score to start a new game session.
     */
    void newGame();
    /**
     * Holds the current falling brick and updates the hold slot.
     *
     * @return ViewData of the new falling brick after holding
     */
    ViewData holdBrick();

    /**
     * Retrieves the matrix of the currently held Tetromino.
     *
     * @return 2D array of held brick shape or null if none held
     */
    int[][] getHeldBrickMatrix();

}
