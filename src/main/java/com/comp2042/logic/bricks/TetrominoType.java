package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;

/**
 * Represents the seven standard tetromino types used in the game.
 *
 * <p>
 * Each type is associated with a specific color for rendering and a unique ID.
 * The ID corresponds to the ordinal position plus one, useful for matrix
 * representations in {@link BrickShapes}.
 * </p>
 */

public enum TetrominoType {
    I(Color.web("#A0F0FF")),   // pastel cyan
    O(Color.web("#FFF4A3")),   // pastel yellow
    T(Color.web("#D8B0FF")),   // lavender
    S(Color.web("#B7FFCC")),   // mint green
    Z(Color.web("#FFB0B0")),   // soft pink
    J(Color.web("#FFD1A8")),   // peach
    L(Color.web("#E3C8FF"));   // light lilac

    /**
     * The color associated with this tetromino type.
     */
    private final Color color;

    /**
     * Constructs a {@code TetrominoType} with the specified color.
     *
     * @param color the color for this tetromino type
     */
    TetrominoType(Color color) {
        this.color = color;
    }

    /**
     * Returns the color associated with this tetromino type.
     *
     * @return the {@link Color} of this tetromino
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the numeric ID of this tetromino type.
     * The ID is calculated as the ordinal position plus one, matching the
     * convention used in {@link BrickShapes} matrices.
     *
     * @return the integer ID of this tetromino
     */
    public int getId() {
        return this.ordinal() + 1;
    }
}
