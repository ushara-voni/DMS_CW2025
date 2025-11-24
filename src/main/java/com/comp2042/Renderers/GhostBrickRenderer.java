package com.comp2042.Renderers;

import com.comp2042.UI.ViewData;
import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GhostBrickRenderer {

    private final int brickSize;
    private final double opacity = 0.5;
    private Rectangle[][] ghostMatrix;
    private final GridPane gamePanel;

    public GhostBrickRenderer(GridPane gamePanel, int brickSize) {
        this.gamePanel = gamePanel;
        this.brickSize = brickSize;
    }

    private void ensureGhostArray(int rows, int cols) {
        if (ghostMatrix != null) return;

        ghostMatrix = new Rectangle[rows][cols];
        for (int r = 2; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle rect = new Rectangle(brickSize, brickSize);
                rect.setFill(Color.TRANSPARENT);
                rect.setOpacity(opacity);
                rect.setVisible(false);
                rect.setMouseTransparent(true);
                rect.getStyleClass().addAll("cell-style", "ghost");

                ghostMatrix[r][c] = rect;
                gamePanel.add(rect, c, r - 2);
            }
        }
    }

    private void clearGhost() {
        if (ghostMatrix == null) return;
        for (int r = 2; r < ghostMatrix.length; r++) {
            for (int c = 0; c < ghostMatrix[r].length; c++) {
                if (ghostMatrix[r][c] != null)
                    ghostMatrix[r][c].setVisible(false);
            }
        }
    }

    private boolean collides(int[][] board, int[][] shape, int x, int y) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 0) continue;

                int br = y + r;
                int bc = x + c;

                if (br < 0 || br >= board.length) return true;
                if (bc < 0 || bc >= board[0].length) return true;
                if (board[br][bc] != 0) return true;
            }
        }
        return false;
    }

    public void renderGhost(ViewData viewData, int[][] board) {
        int[][] shape = viewData.getBrickData();
        int startX = viewData.getxPosition();
        int startY = viewData.getyPosition();

        ensureGhostArray(board.length, board[0].length);
        clearGhost();

        int ghostY = startY;
        while (!collides(board, shape, startX, ghostY + 1)) {
            ghostY++;
        }

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 0) continue;

                int br = ghostY + r;
                int bc = startX + c;

                if (br < 2 || br >= ghostMatrix.length) continue;

                Rectangle g = ghostMatrix[br][bc];

                Paint base = TetrominoType.values()[shape[r][c] - 1].getColor();
                Color col = (Color) base;

                g.setFill(Color.color(col.getRed(), col.getGreen(), col.getBlue(), opacity));
                g.setArcHeight(9);
                g.setArcWidth(9);
                g.setVisible(true);
            }
        }
    }
}
