package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;

public enum TetrominoType {
    I(Color.AQUA),
    O(Color.YELLOW),
    T(Color.BLUEVIOLET),
    S(Color.DARKGREEN),
    Z(Color.RED),
    J(Color.BEIGE),
    L(Color.BURLYWOOD);

    private final Color color;

    TetrominoType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return this.ordinal() + 1;
    }

}
