package com.comp2042.Renderers;

import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Renders the held piece in the Hold panel.
 */
public class HoldPieceRenderer {

    private final GridPane holdPiecePanel;

    public HoldPieceRenderer(GridPane holdPiecePanel) {
        this.holdPiecePanel = holdPiecePanel;
    }

    /**
     * Render the held piece.
     * @param heldBrick Matrix of the held brick, or null if empty.
     */
    public void renderHoldPiece(int[][] heldBrick) {
        // Clear the panel before rendering
        holdPiecePanel.getChildren().clear();

        if (heldBrick == null) {
            return; // nothing to show
        }

        for (int row = 0; row < heldBrick.length; row++) {
            for (int col = 0; col < heldBrick[row].length; col++) {

                if (heldBrick[row][col] != 0) {
                    Rectangle rect = new Rectangle(18, 18);
                    rect.setFill(getFillColor(heldBrick[row][col]));
                    rect.setArcWidth(6);
                    rect.setArcHeight(6);

                    // Add to grid
                    holdPiecePanel.add(rect, col, row);
                }
            }
        }
    }

    private Color getFillColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return TetrominoType.values()[id - 1].getColor();
    }
}
