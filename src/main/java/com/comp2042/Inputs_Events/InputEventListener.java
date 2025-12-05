package com.comp2042.Inputs_Events;


import com.comp2042.logic.core.ClearRow;
import com.comp2042.logic.core.ViewData;

/**
 * Defines the set of game actions that respond to user input or automated
 * game events. Implementations of this interface handle movement, rotation,
 * hard dropping, row clearing, and game state queries.
 * Each input event returns a {@link ViewData} object representing the
 * updated falling brick state, or {@code null} if no visual update
 * is required.
 */

public interface InputEventListener {

    /**
     * Handles a standard downward movement (soft drop).
     *
     * @param event the movement event metadata
     * @return updated view data, or null if no update is needed
     */
    ViewData onDownEvent(MoveEvent event);

    /**
     * Handles a hard drop event, instantly moving the current brick
     * to the lowest valid position.
     *
     * @param event the movement event metadata
     * @return updated view data after the hard drop
     */
    ViewData onHardDropEvent(MoveEvent event);

    /**
     * Handles movement of the current brick to the left.
     *
     * @param event the movement event metadata
     * @return updated view data, or null if movement is blocked
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles movement of the current brick to the right.
     *
     * @param event the movement event metadata
     * @return updated view data, or null if movement is blocked
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles rotation of the current brick.
     *
     * @param event the movement event metadata
     * @return updated view data, or null if rotation is invalid
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Returns the board matrix representing all fixed blocks.
     *
     * @return a 2D integer array of the current board state
     */
    int[][] getBoardMatrix();

    /**
     * Starts a new game, resetting the board, score, and active brick.
     */
    void createNewGame();


    /**
     * Retrieves the most recently cleared row data, if any.
     * The returned value should be consumed once and then cleared.
     *
     * @return a {@link ClearRow} instance describing lines cleared, or null
     */
    ClearRow pollLastClearRow();

    /**
     * Handles the hold action, swapping or storing the active brick.
     *
     * @param event the movement event metadata
     * @return updated view data for the new active brick after holding
     */
    ViewData onHoldEvent(MoveEvent event);

    /**
     * Retrieves the matrix for the currently held brick.
     *
     * @return a 2D array representing the held brick shape, or null if empty
     */
    int[][] getHeldBrickMatrix();


}
