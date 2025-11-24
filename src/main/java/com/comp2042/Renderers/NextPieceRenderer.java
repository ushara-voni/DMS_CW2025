package com.comp2042.Renderers;

import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NextPieceRenderer {

    private final GridPane nextPiecePanel;

    public NextPieceRenderer(GridPane nextPiecePanel) {
        this.nextPiecePanel = nextPiecePanel;
    }

    /** Render the next piece */
    public void renderNextPiece(int[][] nextBrick) {
        nextPiecePanel.getChildren().clear();

        for (int i = 0; i < nextBrick.length; i++) {
            for (int j = 0; j < nextBrick[i].length; j++) {
                if (nextBrick[i][j] != 0) {
                    Rectangle rect = new Rectangle(18, 18);
                    rect.setFill(getFillColor(nextBrick[i][j]));
                    rect.setArcWidth(6);
                    rect.setArcHeight(6);
                    nextPiecePanel.add(rect, j, i);
                }
            }
        }
    }

    private Color getFillColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return TetrominoType.values()[id - 1].getColor();
    }
}
