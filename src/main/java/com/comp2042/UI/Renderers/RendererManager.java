package com.comp2042.UI.Renderers;

import com.comp2042.logic.core.ViewData;
import javafx.scene.layout.GridPane;

/**
 * Coordinates all rendering components of the Tetris UI.
 *
 * This manager integrates:
 * <ul>
 *     <li>{@link BoardRenderer} for the locked blocks and background</li>
 *     <li>{@link BrickRenderer} for falling and ghost pieces</li>
 *     <li>{@link PreviewRenderer} for the next and hold piece panels</li>
 * </ul>
 * It provides unified methods to initialize and render each component.
 */
public class RendererManager {

    /** Renderer for the board and locked tiles. */
    private final BoardRenderer boardRenderer;

    /** Unified renderer for falling and ghost bricks. */
    private final BrickRenderer brickRenderer;

    /** Renderer for the next-piece preview panel. */
    private final PreviewRenderer nextRenderer;
    /** Renderer for the hold-piece preview panel. */
    private final PreviewRenderer holdRenderer;


    /**
     * Constructs a renderer manager to coordinate all Tetris rendering systems.
     *
     * @param boardPanel      the GridPane for locked blocks and ghost projection
     * @param brickPanel      the GridPane for the falling tetromino
     * @param nextPiecePanel  the GridPane for the next piece preview
     * @param holdPiecePanel  the GridPane for the hold piece preview
     * @param brickSize       the size in pixels of each block
     */
    public RendererManager(
            GridPane boardPanel,
            GridPane brickPanel,
            GridPane nextPiecePanel,
            GridPane holdPiecePanel,
            int brickSize
    ) {
        this.boardRenderer = new BoardRenderer(boardPanel, brickSize);

        // NEW: unified renderer handles both falling + ghost
        this.brickRenderer = new BrickRenderer(brickPanel, boardPanel, brickSize);

        this.nextRenderer = new PreviewRenderer(nextPiecePanel, 18);
        this.holdRenderer = new PreviewRenderer(holdPiecePanel, 18);
    }



    /**
     * Initializes the board renderer with the given matrix.
     *
     * @param matrix the board's current state
     */
    public void initBoard(int[][] matrix) {
        boardRenderer.initBoard(matrix);
    }

    /**
     * Refreshes the board rendering with the provided matrix.
     *
     * @param matrix the board's current state
     */
    public void renderBoard(int[][] matrix) {
        boardRenderer.refreshGameBackground(matrix);
    }

    /**
     * Renders the falling brick on the board.
     *
     * @param brick the view data for the falling piece
     */
    public void renderFalling(ViewData brick) {
        brickRenderer.renderFalling(brick);
    }



    /**
     * Renders the ghost piece based on the current falling brick
     * and board state.
     *
     * @param brick the view data for the falling piece
     * @param board the current board matrix
     */
    public void renderGhost(ViewData brick, int[][] board) {
        brickRenderer.renderGhost(brick, board);
    }


    /**
     * Renders the next-piece preview panel.
     *
     * @param next the view data for the next piece
     */
    public void renderNextPiece(ViewData next) {
        nextRenderer.render(next.getNextBrickData());
    }

    /**
     * Renders the hold-piece preview panel.
     *
     * @param held the matrix of the currently held piece
     */
    public void renderHoldPiece(int[][] held) {
        holdRenderer.render(held);
    }
}
