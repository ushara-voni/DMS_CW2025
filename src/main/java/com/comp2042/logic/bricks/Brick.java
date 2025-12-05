package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Represents a Tetris-like brick composed of one or more 2D shape matrices.
 * A {@code Brick} holds a reference to a {@link TetrominoType}, which defines all
 * rotation states of the piece. The brick tracks its current rotation index and
 * provides access to the corresponding shape matrix.
 */
public class Brick {

    /** The type of tetromino this brick represents. */
    private final TetrominoType type;
    /** The current rotation index (0 to rotationCount-1). */
    private int rotation = 0;

    /**
     * Constructs a new {@code Brick} of the given tetromino type.
     *
     * @param type the {@link TetrominoType} that defines the shape and rotations
     */
    public Brick(TetrominoType type) {
        this.type = type;
    }


    /**
     * Returns the shape matrix of the brick in its current rotation state.
     * The returned 2D array contains integer values indicating whether each cell
     * is filled (typically 1) or empty (typically 0).
     *
     * @return a 2D integer array representing the current rotation's shape matrix
     */
    public int[][] getShape() {
        return type.getRotation(rotation);
    }

    /**
     * Rotates the brick clockwise to the next rotation state.
     * If the brick has reached its last rotation state, this method wraps back to
     * the first rotation (i.e., modulo rotation count).
     */
    public void rotate() {
        rotation = (rotation + 1) % type.rotationCount();
    }

    /**
     * Resets the brick to its default rotation (usually the 0° orientation).
     */
    public void resetRotation() {
        rotation = 0;
    }

    /**
     * Returns the tetromino type associated with this brick.
     *
     * @return the {@link TetrominoType} defining this brick
     */
    public TetrominoType getType() {
        return type;
    }

    /**
     * Returns the default (unrotated) shape matrix of this brick.
     * This is equivalent to obtaining rotation index {@code 0} from the
     * {@link TetrominoType}.
     *
     * @return a 2D integer array representing the default rotation's shape matrix
     */
    public int[][] getShapeMatrix() {
        return type.getRotation(0);
    }
}

