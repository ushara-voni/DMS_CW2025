package com.comp2042.UI.Renderers;

import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Renders a tetromino preview inside a small 2D grid panel.
 * This renderer is used for the "Next Piece" and "Hold Piece" displays.
 * It draws a compact version of the tetromino using a simple matrix
 * representation, without applying board offsets or ghost effects.
 */
public class PreviewRenderer {

    /** The preview panel where the tetromino will be rendered. */
    private final GridPane panel;

    /** Size (in pixels) of each cell in the preview display. */
    private final int cellSize;

    /**
     * Creates a preview renderer for a specific UI panel.
     *
     * @param panel    the {@link GridPane} where the preview will be drawn
     * @param cellSize pixel size of each tetromino cell in the preview
     */
    public PreviewRenderer(GridPane panel, int cellSize) {
        this.panel = panel;
        this.cellSize = cellSize;
    }

    /**
     * Renders a tetromino matrix into the preview panel.
     * The method clears any previous content and draws only the colored
     * cells found in the provided matrix. Cells with value {@code 0}
     * are ignored. Values {@code 1–7} map to {@link TetrominoType}
     * colors.
     *
     * @param piece a 2D matrix describing the tetromino shape,
     *              or {@code null} to clear the panel
     */
    public void render(int[][] piece) {
        panel.getChildren().clear();
        if (piece == null) return;

        for (int r = 0; r < piece.length; r++) {
            for (int c = 0; c < piece[r].length; c++) {
                if (piece[r][c] == 0) continue;
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill(TetrominoType.values()[piece[r][c] - 1].getColor());
                rect.setArcWidth(6);
                rect.setArcHeight(6);
                panel.add(rect, c, r);
            }
        }
    }
}
