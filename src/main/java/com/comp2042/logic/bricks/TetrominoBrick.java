package com.comp2042.logic.bricks;

import java.util.List;

public class TetrominoBrick implements Brick {

    private final TetrominoType type;

    public TetrominoBrick(TetrominoType type) {
        this.type = type;
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return BrickShapes.SHAPES.get(type);
    }

    public TetrominoType getType() {
        return type;
    }
}
