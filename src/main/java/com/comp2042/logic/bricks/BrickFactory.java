package com.comp2042.logic.bricks;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Factory class for creating {@link Brick} instances.
 *
 * This class provides methods to create specific bricks based on a given
 * {@link TetrominoType} as well as to generate random bricks for gameplay.
 */
public class BrickFactory {
    /**
     * Creates a {@link Brick} instance for the specified tetromino type.
     *
     * @param type the type of tetromino to create
     * @return a new {@link TetrominoBrick} instance representing the given type
     */
    public static Brick createBrick(TetrominoType type) {
        return new TetrominoBrick(type);
    }

    /**
     * Creates a {@link Brick} instance of a randomly selected tetromino type.
     *
     * @return a new {@link TetrominoBrick} with a randomly chosen shape
     */
    public static Brick createRandomBrick() {
        TetrominoType[] types = TetrominoType.values();
        TetrominoType randomType = types[ThreadLocalRandom.current().nextInt(types.length)];
        return new TetrominoBrick(randomType);
    }
}
