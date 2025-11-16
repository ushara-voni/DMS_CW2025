package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final List<TetrominoBrick> brickList;
    private final Deque<TetrominoBrick> nextBricks = new ArrayDeque<>();

    public RandomBrickGenerator() {
        brickList = new ArrayList<>();

        // Create one brick for each Tetromino type
        for (TetrominoType type : TetrominoType.values()) {
            brickList.add(new TetrominoBrick(type));
        }

        // Pre-fill queue with 2 random bricks
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
    }

    private TetrominoBrick randomBrick() {
        return brickList.get(ThreadLocalRandom.current().nextInt(brickList.size()));
    }

    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(randomBrick());
        }
        return nextBricks.poll();
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
