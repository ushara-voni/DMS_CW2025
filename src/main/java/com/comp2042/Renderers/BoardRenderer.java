package com.comp2042.Renderers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardRenderer {

    private final GridPane gamePanel;
    private final int brickSize;
    private Rectangle[][] displayMatrix;

    public BoardRenderer(GridPane gamePanel, int brickSize) {
        this.gamePanel = gamePanel;
        this.brickSize = brickSize;
    }

    /** Create the background cells (only called once) */
    public void initBoard(int[][] boardMatrix) {

        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];

        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {

                Rectangle rect = new Rectangle(brickSize, brickSize);
                rect.setFill(Color.TRANSPARENT);
                rect.getStyleClass().add("cell-style");

                // Rounded edges
                rect.setArcWidth(9);
                rect.setArcHeight(9);

                displayMatrix[i][j] = rect;
                gamePanel.add(rect, j, i - 2);
            }
        }
    }

    /** Update the background after lines clear */
    public void refreshGameBackground(int[][] board) {
        if (displayMatrix == null) return;

        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Rectangle rect = displayMatrix[i][j];
                int id = board[i][j];

                rect.setFill(getColor(id));
                rect.setArcWidth(9);   // reapply round corners
                rect.setArcHeight(9);
            }
        }
    }

    private Color getColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return com.comp2042.logic.bricks.TetrominoType.values()[id - 1].getColor();
    }
}
