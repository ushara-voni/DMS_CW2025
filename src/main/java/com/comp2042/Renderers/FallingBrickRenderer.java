package com.comp2042.Renderers;

import com.comp2042.UI.ViewData;
import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FallingBrickRenderer {


    private static final int BOARD_ROW_OFFSET = 1;

    private final GridPane brickPanel;
    private final int brickSize;
    private Rectangle[][] rectangles;

    public FallingBrickRenderer(GridPane brickPanel, int brickSize) {
        this.brickPanel = brickPanel;
        this.brickSize = brickSize;
    }

    public void init(ViewData brick) {
        int[][] shape = brick.getBrickData();
        if (shape == null) return;

        int rows = shape.length;
        int cols = shape[0].length;

        rectangles = new Rectangle[rows][cols];
        brickPanel.getChildren().clear();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle rect = new Rectangle(brickSize, brickSize);
                rect.getStyleClass().addAll("cell-style", "brick");
                rect.setArcWidth(9);
                rect.setArcHeight(9);
                rect.setFill(Color.TRANSPARENT);
                rect.setVisible(false);

                rectangles[r][c] = rect;
                brickPanel.add(rect, 0, 0);
            }
        }

        render(brick);
    }

    public void render(ViewData brick) {
        if (rectangles == null) {
            init(brick);
            if (rectangles == null) return;
        }


        int[][] shape = brick.getBrickData();

        for (int r = 0; r < rectangles.length; r++) {
            for (int c = 0; c < rectangles[r].length; c++) {
                Rectangle rect = rectangles[r][c];
                if (rect == null) continue;

                boolean filled = r < shape.length && c < shape[r].length && shape[r][c] != 0;
                rect.setFill(filled ? getFillColor(shape[r][c]) : Color.TRANSPARENT);
                rect.setVisible(filled);
                rect.setArcWidth(9);
                rect.setArcHeight(9);

                // Skip if cell is “above” the board
                if (!filled) continue;

                // Always start at row 0, column 1
                GridPane.setRowIndex(rect, r);
                GridPane.setColumnIndex(rect, c );
            }
        }


    }


    private Paint getFillColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return TetrominoType.values()[id - 1].getColor();
    }

}
