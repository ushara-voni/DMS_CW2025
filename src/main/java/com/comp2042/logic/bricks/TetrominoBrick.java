package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Represents a specific tetromino brick in the game.
 *
 * Each {@code TetrominoBrick} has a {@link TetrominoType} and provides its
 * corresponding shape matrices from {@link BrickShapes}. The shape matrices
 * include all rotation states of the brick.
 *
 */

public class TetrominoBrick implements Brick {

    /**
     * The type of this tetromino brick.
     */
    private final TetrominoType type;

    /**
     * Constructs a {@code TetrominoBrick} of the specified {@link TetrominoType}.
     *
     * @param type the type of tetromino to create
     */
    public TetrominoBrick(TetrominoType type) {
        this.type = type;
    }

    /**
     * Returns the list of 2D shape matrices for this tetromino.
     *
     * Each matrix corresponds to a rotation state. The matrices are retrieved
     * from {@link BrickShapes}.
     *
     *
     * @return a list of 2D integer arrays representing the brick's shape
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return BrickShapes.SHAPES.get(type);
    }

    /**
     * Returns the {@link TetrominoType} of this brick.
     *
     * @return the type of this tetromino brick
     */
    public TetrominoType getType() {
        return type;
    }
}
