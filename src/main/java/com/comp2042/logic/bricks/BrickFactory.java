package com.comp2042.logic.bricks;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Factory class responsible for creating {@link Brick} instances.
 * This class provides utility methods for constructing bricks of specific
 * {@link TetrominoType}s as well as generating random bricks during gameplay.
 * It encapsulates object creation to keep brick construction consistent
 * throughout the game logic.
 */
public class BrickFactory {

    /**
     * Creates a new {@link Brick} instance based on the specified tetromino type.
     *
     * @param type the {@link TetrominoType} representing the brick's shape and rotations
     * @return a new {@code Brick} of the given type
     */
    public static Brick createBrick(TetrominoType type) {
        return new Brick(type);
    }


    /**
     * Creates a new {@link Brick} instance using a randomly selected
     * {@link TetrominoType}.
     * The random selection uses {@link ThreadLocalRandom} to choose one of the
     * available tetromino types defined in {@link TetrominoType#values()}.
     *
     * @return a new {@code Brick} with a random tetromino type
     */
    public static Brick createRandomBrick() {
        TetrominoType[] types = TetrominoType.values();
        int rand = ThreadLocalRandom.current().nextInt(types.length);
        return new Brick(types[rand]);
    }
}
