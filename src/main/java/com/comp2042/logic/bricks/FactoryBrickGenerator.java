package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementation of {@link BrickGenerator} that generates bricks using {@link BrickFactory}.
 *
 * <p>
 * Maintains a queue of upcoming bricks to provide the current brick and the next
 * brick in line, similar to the "next piece" feature in Tetris.
 * </p>
 */

public class FactoryBrickGenerator implements BrickGenerator {

    /**
     * Queue holding upcoming bricks. The first element is the current brick, and
     * the second element is the next brick.
     */
    private final Deque<Brick> queue = new ArrayDeque<>();

    /**
     * Constructs a {@code FactoryBrickGenerator} and initializes the queue
     * with two random bricks.
     */
    public FactoryBrickGenerator() {
        queue.add(BrickFactory.createRandomBrick());
        queue.add(BrickFactory.createRandomBrick());
    }
    /**
     * Returns and removes the current brick from the queue.
     * If the queue has fewer than two bricks after polling, a new random brick
     * is automatically added to maintain the queue size.
     *
     *
     * @return the current {@link Brick} to be used in the game
     */
    @Override
    public Brick getBrick() {
        if (queue.size() < 2) {
            queue.add(BrickFactory.createRandomBrick());
        }
        return queue.poll();
    }
    /**
     * Returns the next brick in the queue without removing it.
     * <p>
     * This is typically used to preview the next piece in the game.
     * </p>
     *
     * @return the next {@link Brick} in the queue
     */
    @Override
    public Brick getNextBrick() {
        return queue.peek();
    }
}
