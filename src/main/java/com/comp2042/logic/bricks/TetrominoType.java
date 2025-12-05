package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;
/**
 * Represents all Tetris tetromino types and their respective rotation states.
 * 
 * Each enum constant defines:
 * <ul>
 *     <li>A set of 2D matrices representing the shape in each rotation.</li>
 *     <li>A display color associated with the tetromino.</li>
 * </ul>
 * The matrices use integers to indicate filled cells. Each tetromino may have
 * between 1 and 4 rotation states depending on its geometry.
 *
 */
public enum TetrominoType {
    /** The I-shaped tetromino (line piece) with 2 rotation states. */
    I(new int[][][]{
            {
                    {0,0,0,0},
                    {1,1,1,1},
                    {0,0,0,0},
                    {0,0,0,0}
            },
            {
                    {0,1,0,0},
                    {0,1,0,0},
                    {0,1,0,0},
                    {0,1,0,0}
            }
    },Color.web("#A0F0FF")),
    /** The O-shaped tetromino (square piece) with only 1 rotation state. */
    O(new int[][][]{
            {
                    {0,0,0,0},
                    {0,2,2,0},
                    {0,2,2,0},
                    {0,0,0,0}
            }
    },Color.web("#FFF4A3")),
    /** The T-shaped tetromino with 4 rotation states. */
    T(new int[][][]{
            {
                    {0,0,0},
                    {3,3,3},
                    {0,3,0}
            },
            {
                    {0,3,0},
                    {3,3,0},
                    {0,3,0}
            },
            {
                    {0,3,0},
                    {3,3,3},
                    {0,0,0}
            },
            {
                    {0,3,0},
                    {0,3,3},
                    {0,3,0}
            }
    },Color.web("#D8B0FF")),
    /** The L-shaped tetromino with 4 rotation states. */
    L(new int[][][]{
            {
                    {4,0,0},
                    {4,4,4},
                    {0,0,0}
            },
            {
                    {0,4,4},
                    {0,4,0},
                    {0,4,0}
            },
            {
                    {0,0,0},
                    {4,4,4},
                    {0,0,4}
            },
            {
                    {0,4,0},
                    {0,4,0},
                    {4,4,0}
            }
    },Color.web("#E3C8FF")),
    /** The J-shaped tetromino with 4 rotation states. */
    J(new int[][][]{
            {   // 0 degrees
                    {0,0,5},
                    {5,5,5},
                    {0,0,0}
            },
            {   // 90 degrees
                    {0,5,0},
                    {0,5,0},
                    {5,5,0}
            },
            {   // 180 degrees
                    {0,0,0},
                    {5,5,5},
                    {5,0,0}
            },
            {   // 270 degrees
                    {0,5,5},
                    {0,5,0},
                    {0,5,0}
            }
    },Color.web("#FFD1A8")),
    /** The S-shaped tetromino with 2 rotation states. */
    S(new int[][][]{
            {
                    {0,6,6},
                    {6,6,0},
                    {0,0,0}
            },
            {
                    {0,6,0},
                    {0,6,6},
                    {0,0,6}
            }
    },Color.web("#B7FFCC")),
    /** The Z-shaped tetromino with 2 rotation states. */
    Z(new int[][][]{
            {
                    {7,7,0},
                    {0,7,7},
                    {0,0,0}
            },
            {
                    {0,0,7},
                    {0,7,7},
                    {0,7,0}
            }
    },Color.web("#FFB0B0"));

    /** All rotation matrices for this tetromino type. */
    private final int[][][] rotations;

    /** The display color associated with this tetromino. */
    private Color color;

    /**
     * Creates a tetromino type with the given rotations and display color.
     *
     * @param rotations an array of shape matrices, one for each rotation state
     * @param color the color used to render this tetromino
     */
    TetrominoType(int[][][] rotations,Color color) {
        this.rotations = rotations;
        this.color = color;
    }

    /**
     * Returns the shape matrix for the specified rotation index.
     *
     * @param index the rotation index (0 to rotationCount()-1)
     * @return a 2D integer array representing the tetromino shape
     */
    public int[][] getRotation(int index) {
        return rotations[index];
    }

    /**
     * Returns the display color of this tetromino.
     *
     * @return the {@link Color} used for rendering
     */
    public Color getColor() { return color; }

    /**
     * Returns the number of rotation states this tetromino has.
     *
     * @return the number of rotation matrices
     */
    public int rotationCount() {
        return rotations.length;
    }
}
