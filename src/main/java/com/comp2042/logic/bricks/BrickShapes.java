package com.comp2042.logic.bricks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the predefined 2D shape matrices for all {@link TetrominoType} bricks.
 *
 *
 * Each tetromino type is associated with a list of rotation states. Each rotation
 * state is represented as a 2D integer array where:
 *
 * <ul>
 *   <li>0 indicates an empty cell</li>
 *   <li>1–7 indicate filled cells corresponding to the tetromino type</li>
 * </ul>
 *
 *
 * This class is used by {@link TetrominoBrick} to retrieve the shape matrices
 * for rendering and rotation logic in the game.
 *
 */
public class BrickShapes {
    /**
     * A map linking each {@link TetrominoType} to its list of rotation matrices.
     *
     * The list contains one or more 4x4 matrices, each representing a rotation
     * state of the tetromino.
     *
     */
    public static final Map<TetrominoType, List<int[][]>> SHAPES = new HashMap<>();

    static {
        SHAPES.put(TetrominoType.I, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.O, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 2, 2, 0},
                        {0, 2, 2, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 2, 2, 0},
                        {0, 2, 2, 0},
                        {0, 0, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.T, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {3, 3, 3, 0},
                        {0, 3, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 3, 0, 0},
                        {0, 3, 3, 0},
                        {0, 3, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 3, 0, 0},
                        {3, 3, 3, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 3, 0, 0},
                        {3, 3, 0, 0},
                        {0, 3, 0, 0},
                        {0, 0, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.S, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 4, 4, 0},
                        {4, 4, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {4, 0, 0, 0},
                        {4, 4, 0, 0},
                        {0, 4, 0, 0},
                        {0, 0, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.Z, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {5, 5, 0, 0},
                        {0, 5, 5, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 5, 0, 0},
                        {5, 5, 0, 0},
                        {5, 0, 0, 0},
                        {0, 0, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.J, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {6, 6, 6, 0},
                        {0, 0, 6, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 6, 0, 0}
                },
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 6, 0, 0},
                        {0, 6, 6, 6},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 0, 6, 0},
                        {0, 0, 6, 0},
                        {0, 6, 6, 0},
                        {0, 0, 0, 0}
                }
        ));

        SHAPES.put(TetrominoType.L, Arrays.asList(
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 7, 7, 7},
                        {0, 7, 0, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 7, 7, 0},
                        {0, 0, 7, 0},
                        {0, 0, 7, 0}
                },
                new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 7, 0},
                        {7, 7, 7, 0},
                        {0, 0, 0, 0}
                },
                new int[][]{
                        {0, 7, 0, 0},
                        {0, 7, 0, 0},
                        {0, 7, 7, 0},
                        {0, 0, 0, 0}
                }
        ));
    }
}
