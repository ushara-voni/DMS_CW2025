package com.comp2042.Renderers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Handles rendering of the game board in a Tetris-style game using JavaFX.
 * This class manages a grid of {@link Rectangle} objects representing each
 * cell in the game board. It can initialize the board, update it after line
 * clears, and map tetromino IDs to their corresponding colors.
 */
public class BoardRenderer {

    /** The JavaFX panel where the game board is displayed */
    private final GridPane gamePanel;

    /** Size (in pixels) of each individual brick */
    private final int brickSize;

    /** 2D array of {@link Rectangle} representing each cell in the board */
    private Rectangle[][] displayMatrix;


    /**
     * Constructs a {@code BoardRenderer} for a given game panel and brick size.
     *
     * @param gamePanel the JavaFX {@link GridPane} to render the board on
     * @param brickSize the size (width and height) of each brick in pixels
     */
    public BoardRenderer(GridPane gamePanel, int brickSize) {
        this.gamePanel = gamePanel;
        this.brickSize = brickSize;
    }


    /**
     * Initializes the board with empty cells and adds them to the game panel.
     * <p>
     * Typically called once at the start of the game.
     * </p>
     *
     * @param boardMatrix a 2D integer array representing the board layout
     */
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


    /**
     * Updates the board display to reflect the current state of the game matrix.
     * Should be called after clearing lines or moving tetrominoes.
     *
     * @param board a 2D integer array representing the current state of the board
     */
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
    /**
     * Returns the color corresponding to a tetromino ID.
     *
     * @param id the tetromino ID (0 for empty cell)
     * @return the {@link Color} representing the tetromino type, or transparent if empty
     */
    private Color getColor(int id) {
        if (id == 0) return Color.TRANSPARENT;
        return com.comp2042.logic.bricks.TetrominoType.values()[id - 1].getColor();
    }
}
