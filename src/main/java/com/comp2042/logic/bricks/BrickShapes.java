package com.comp2042.logic.bricks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrickShapes {

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
