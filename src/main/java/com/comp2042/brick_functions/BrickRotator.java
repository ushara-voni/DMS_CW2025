package com.comp2042.brick_functions;

import com.comp2042.UI.ViewData;
import com.comp2042.logic.bricks.Brick;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Replaces old getNextShape() that returned NextShapeInfo.
     * Now returns the next shape matrix directly.
     */
    public int[][] getNextShape() {
        int nextShapeIndex = (currentShape + 1) % brick.getShapeMatrix().size();
        return brick.getShapeMatrix().get(nextShapeIndex);
    }

    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Optional helper: return a full ViewData object for rendering.
     * Can be used by GameController or GUI.
     */
    public ViewData getViewData(int xPosition, int yPosition) {
        return new ViewData(
                getCurrentShape(),
                xPosition,
                yPosition,
                getNextShape()
        );
    }

    public int getCurrentShapeIndex() {
        return currentShape;
    }

    public int getShapeCount() {
        return brick.getShapeMatrix().size();
    }

}
