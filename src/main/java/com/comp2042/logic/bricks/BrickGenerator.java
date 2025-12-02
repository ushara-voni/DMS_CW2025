package com.comp2042.logic.bricks;

/**
 * Provides an interface for generating {@link Brick} instances.
 *
 * Implementations typically handle the logic for supplying the current brick
 * and previewing the next one, such as in a Tetris-style game.
 */

public interface BrickGenerator {

    /**
     * Returns the current active brick to be used in the game.
     *
     * @return the current {@link Brick}
     */
    Brick getBrick();

    /**
     * Returns the next brick that will appear after the current one.
     *
     * This is commonly used for "Next Piece" previews in Tetris-style games.
     *
     * @return the next {@link Brick}
     */
    Brick getNextBrick();
}
