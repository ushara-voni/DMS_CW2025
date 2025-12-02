package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Represents a Tetris-like brick composed of one or more 2D shape matrices.
 *
 * Each matrix in the returned list typically corresponds to a rotation state
 * of the brick (e.g., 0°, 90°, 180°, 270°). The matrices use integers to
 * indicate filled or empty cells.
 */
public interface Brick {

    /**
     * Returns the list of shape matrices representing this brick.
     *
     * Each matrix is a 2D integer array where:
     * <ul>
     *   <li>1 indicates an occupied cell</li>
     *   <li>0 indicates an empty cell</li>
     * </ul>
     *
     * @return a list of 2D integer arrays representing the brick’s shape in each rotation state
     */
    List<int[][]> getShapeMatrix();

}
