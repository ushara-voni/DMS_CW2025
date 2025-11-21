package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;

public enum TetrominoType {
    I(Color.web("#A0F0FF")),   // pastel cyan
    O(Color.web("#FFF4A3")),   // pastel yellow
    T(Color.web("#D8B0FF")),   // lavender
    S(Color.web("#B7FFCC")),   // mint green
    Z(Color.web("#FFB0B0")),   // soft pink
    J(Color.web("#FFD1A8")),   // peach
    L(Color.web("#E3C8FF"));   // light lilac

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
