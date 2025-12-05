package com.comp2042.UI.Renderers;

import com.comp2042.logic.core.ViewData;
import com.comp2042.logic.bricks.TetrominoType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Renders both the falling tetromino and its ghost projection on the board.
 * This class merges the responsibilities of the former
 * {@code FallingBrickRenderer} and {@code GhostBrickRenderer},
 * allowing unified drawing logic for Tetris pieces.
 *
 * Responsibilities include:
 * <ul>
 *     <li>Initializing and rendering the 4×4 falling tetromino grid</li>
 *     <li>Computing ghost drop position</li>
 *     <li>Rendering the ghost piece over the main board</li>
 * </ul>
 */
public class BrickRenderer {


    /** Number of invisible rows above the visible board. */
    private static final int BOARD_OFFSET = 2;

    /** Maximum dimension of a tetromino (4×4). */
    private static final int MAX_SHAPE = 4;

    /** Opacity value applied to ghost bricks. */
    private final double ghostOpacity = 0.45;

    /** Panel where the falling piece's 4×4 grid is rendered. */
    private final GridPane fallingPanel;

    /** Panel representing the board where the ghost piece is rendered. */
    private final GridPane boardPanel;

    /** Pixel size of an individual brick cell. */
    private final int brickSize;

    /** 4×4 grid of rectangles used for the falling tetromino. */
    private Rectangle[][] fallingRects;

    /** Full board matrix of rectangles used for the ghost projection. */
    private Rectangle[][] ghostRects;


    /**
     * Creates a renderer capable of drawing both falling and ghost pieces.
     *
     * @param fallingPanel the GridPane dedicated to the falling tetromino
     * @param boardPanel   the GridPane representing the main board
     * @param brickSize    the size (in pixels) of each brick cell
     */
    public BrickRenderer(GridPane fallingPanel, GridPane boardPanel, int brickSize) {
        this.fallingPanel = fallingPanel;
        this.boardPanel = boardPanel;
        this.brickSize = brickSize;
    }


    /**
     * Initializes and renders the 4×4 grid used for drawing the active falling piece.
     *
     * @param brick the current falling piece's view data containing its shape and position
     */
    public void initFalling(ViewData brick) {
        fallingRects = new Rectangle[MAX_SHAPE][MAX_SHAPE];
        fallingPanel.getChildren().clear();

        for (int r = 0; r < MAX_SHAPE; r++) {
            for (int c = 0; c < MAX_SHAPE; c++) {
                Rectangle rect = new Rectangle(brickSize, brickSize);
                rect.getStyleClass().addAll("cell-style", "brick");
                rect.setFill(Color.TRANSPARENT);
                rect.setVisible(false);
                rect.setArcWidth(9);
                rect.setArcHeight(9);
                fallingRects[r][c] = rect;

                fallingPanel.add(rect, c, r);
            }
        }

        renderFalling(brick);
    }


    /**
     * Ensures the ghost rectangle buffer exists.
     * Allocates a rectangle for every visible cell in the board.
     *
     * @param rows total board rows
     * @param cols total board columns
     */
    private void ensureGhostArray(int rows, int cols) {
        if (ghostRects != null) return;

        ghostRects = new Rectangle[rows][cols];

        for (int r = BOARD_OFFSET; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle g = new Rectangle(brickSize, brickSize);
                g.getStyleClass().addAll("cell-style", "ghost");
                g.setFill(Color.TRANSPARENT);
                g.setOpacity(ghostOpacity);
                g.setVisible(false);
                g.setMouseTransparent(true);
                g.setArcWidth(9);
                g.setArcHeight(9);

                ghostRects[r][c] = g;
                boardPanel.add(g, c, r - BOARD_OFFSET);
            }
        }
    }

    /**
     * Hides all ghost rectangles without removing them from the board.
     * Maintains buffer for fast re-rendering.
     */
    private void clearGhost() {
        if (ghostRects == null) return;
        for (Rectangle[] row : ghostRects) {
            for (Rectangle r : row) {
                if (r != null) r.setVisible(false);
            }
        }
    }


    /**
     * Renders the falling tetromino using its 4×4 matrix representation.
     *
     * @param brick view data containing shape and rotation state
     */
    public void renderFalling(ViewData brick) {
        if (fallingRects == null) {
            initFalling(brick);
            return;
        }

        int[][] shape = brick.getBrickData();

        for (int r = 0; r < MAX_SHAPE; r++) {
            for (int c = 0; c < MAX_SHAPE; c++) {

                Rectangle rect = fallingRects[r][c];
                boolean filled = r < shape.length && c < shape[r].length && shape[r][c] != 0;

                rect.setVisible(filled);
                rect.setFill(filled ? getColor(shape[r][c]) : Color.TRANSPARENT);

                if (filled) {
                    GridPane.setRowIndex(rect, r);
                    GridPane.setColumnIndex(rect, c);
                }
            }
        }
    }



    /**
     * Renders the ghost projection of the current falling tetromino.
     * The ghost appears at the lowest valid Y position before collision.
     *
     * @param viewData the falling tetromino's view data
     * @param board    the current board matrix used for collision detection
     */
    public void renderGhost(ViewData viewData, int[][] board) {
        int[][] shape = viewData.getBrickData();
        int x = viewData.getxPosition();
        int y = viewData.getyPosition();

        ensureGhostArray(board.length, board[0].length);
        clearGhost();

        // compute ghost Y
        int ghostY = y;
        while (!collides(board, shape, x, ghostY + 1)) ghostY++;

        // draw ghost
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 0) continue;

                int br = ghostY + r;
                int bc = x + c;

                if (br < BOARD_OFFSET || br >= board.length) continue;

                Rectangle g = ghostRects[br][bc];
                Color col = (Color) getColor(shape[r][c]);

                g.setFill(Color.color(col.getRed(), col.getGreen(), col.getBlue(), ghostOpacity));
                g.setVisible(true);
            }
        }
    }

    /**
     * Checks whether placing the tetromino at the given position would collide
     * with the board boundaries or another block.
     *
     * @param board board matrix
     * @param shape tetromino shape
     * @param x     leftmost position
     * @param y     topmost position
     * @return {@code true} if a collision occurs
     */
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



    /**
     * Returns the color associated with a tetromino ID.
     *
     * @param id the numeric ID of the tetromino (1–7), or 0 for empty
     * @return a {@link Paint} object representing the assigned color
     */
    private Paint getColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return TetrominoType.values()[id - 1].getColor();
    }
}
