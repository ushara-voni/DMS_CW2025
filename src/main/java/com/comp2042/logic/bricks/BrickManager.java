package com.comp2042.logic.bricks;

import com.comp2042.logic.core.BoardGrid;
import com.comp2042.logic.core.ViewData;

import java.awt.*;

/**
 * Manages the lifecycle, movement, rotation, and holding of Tetris bricks.
 * <p>
 * The {@code BrickManager} tracks the current falling brick, the next brick,
 * and the optionally held brick. It also performs collision checks, movement,
 * and integrates bricks into the {@link BoardGrid} once they can no longer move.
 * </p>
 */
public class BrickManager {

    /** The game board grid used for collision checks and merging bricks. */
    private final BoardGrid boardGrid;

    /** The currently active falling brick. */
    private Brick current;
    /** The next brick to be spawned after the current one locks. */
    private Brick next;

    /** The brick stored using the hold mechanic (may be null). */
    private Brick heldBrick = null;
    /** Tracks whether hold has been used during the current turn. */
    private boolean holdUsedThisTurn = false;

    /** The position (x, y) of the current brick on the board grid. */
    private Point currentOffset;

    /**
     * Creates a new {@code BrickManager} for the given board grid.
     * <p>
     * Initializes the current and next bricks randomly and positions
     * the current brick at its initial spawn offset.
     * </p>
     *
     * @param boardGrid the board on which bricks are placed and merged
     */
    public BrickManager(BoardGrid boardGrid) {
        this.boardGrid = boardGrid;

        // INITIAL SEQUENCE
        current = BrickFactory.createRandomBrick();
        next = BrickFactory.createRandomBrick();

        currentOffset = new Point(0, 1);
    }


    /**
     * Spawns the next brick, generates a new upcoming brick,
     * resets rotation, and resets the "hold used" flag.
     *
     * @return {@code true} if spawning is possible (no collision),
     *         {@code false} if the new brick immediately collides (game over)
     */
    public boolean spawnNewBrick() {
        current = next;
        next = BrickFactory.createRandomBrick();

        holdUsedThisTurn = false;
        current.resetRotation();
        currentOffset = new Point(0, 1);

        return !MatrixOperations.intersect(
                boardGrid.getMatrixCopy(),
                current.getShape(),
                currentOffset.x,
                currentOffset.y
        );
    }

    /**
     * Activates the hold mechanic. If a brick is already held,
     * it swaps with the current brick. Otherwise, the current brick is held
     * and a new brick is spawned.
     * <p>
     * This method cannot be used more than once per brick drop.
     * </p>
     *
     * @return the updated {@link ViewData} after the hold action
     */

    public ViewData holdPiece() {
        if (holdUsedThisTurn) return getViewData();

        Brick temp = heldBrick;
        heldBrick = current;
        holdUsedThisTurn = true;

        if (temp == null) {
            spawnNewBrick();
        } else {
            current = temp;
            current.resetRotation();
            currentOffset = new Point(0, 1);
        }

        return getViewData();
    }

    /**
     * Returns the matrix representation of the held brick for preview UI.
     *
     * @return a 2D array for the held piece, or {@code null} if no brick is held
     */
    public int[][] getHeldBrickMatrix() {
        if (heldBrick == null) return null;
        return heldBrick.getShapeMatrix();
    }

    /**
     * Resets the hold system by clearing the held brick
     * and re-enabling hold usage.
     */
    public void resetHold() {
        heldBrick = null;
        holdUsedThisTurn = false;
    }

    /**
     * Returns the matrix representation of the next brick for preview UI.
     *
     * @return a 2D array representing the next piece
     */
    public int[][] getNextShapePreview() {
        return next.getShapeMatrix();
    }


    /**
     * Packages the current game state into {@link ViewData} for rendering.
     * Includes the current brick matrix, its position,
     * and a preview of the next brick.
     *
     * @return a new {@code ViewData} object
     */
    public ViewData getViewData() {
        return new ViewData(
                current.getShape(),
                currentOffset.x,
                currentOffset.y,
                getNextShapePreview()
        );
    }
    /**
     * Returns the current brick's offset on the board.
     * A defensive copy is returned to preserve immutability.
     *
     * @return a copy of the current brick offset
     */
    public Point getCurrentOffset() {
        return new Point(currentOffset);
    }
    /**
     * Moves the current brick one cell downward.
     *
     * @return {@code true} if the move was successful, {@code false} if blocked
     */
    public boolean moveDown() { return move(0, 1); }
    /**
     * Moves the current brick one cell to the left.
     *
     * @return {@code true} if the move was successful, {@code false} if blocked
     */
    public boolean moveLeft() { return move(-1, 0); }
    /**
     * Moves the current brick one cell to the right.
     *
     * @return {@code true} if the move was successful, {@code false} if blocked
     */
    public boolean moveRight() { return move(1, 0); }
    /**
     * Attempts to move the current brick by the specified offset.
     * Performs collision detection before applying the movement.
     *
     * @param dx movement along the x-axis
     * @param dy movement along the y-axis
     * @return {@code true} if movement was successful, {@code false} if blocked
     */
    public boolean move(int dx, int dy) {
        if (currentOffset == null) return false;
        Point newPos = new Point(currentOffset);
        newPos.translate(dx, dy);
        if (MatrixOperations.intersect( boardGrid.getMatrixCopy(), current.getShape(), newPos.x, newPos.y )) return false;
        currentOffset = newPos; return true; }

    /**
     * Attempts to rotate the current brick clockwise.
     * If rotation results in a collision, it is reverted.
     *
     * @return {@code true} if rotation succeeded, {@code false} otherwise
     */
    public boolean rotate() {
        current.rotate();
        if (MatrixOperations.intersect( boardGrid.getMatrixCopy(), current.getShape(), currentOffset.x, currentOffset.y ))
        {
            current.rotate();
            current.rotate();
            current.rotate();
            return false; }
        return true; }

    /**
     * Merges the current brick's blocks permanently into the board grid.
     * This should be called when the brick can no longer move downward.
     */
    public void mergeToBoard() {
        boardGrid.merge( current.getShape(), currentOffset.x, currentOffset.y );
    }

}

