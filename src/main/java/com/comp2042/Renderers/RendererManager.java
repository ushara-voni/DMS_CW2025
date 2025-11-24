package com.comp2042.Renderers;

import com.comp2042.UI.ViewData;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public class RendererManager {

    private final BoardRenderer boardRenderer;
    private final FallingBrickRenderer fallingRenderer;
    private final GhostBrickRenderer ghostRenderer;
    private final NextPieceRenderer nextPieceRenderer;

    public RendererManager(
            GridPane boardPanel,
            GridPane brickPanel,
            GridPane nextPiecePanel,
            int brickSize
    ) {
        this.boardRenderer = new BoardRenderer(boardPanel, brickSize);
        this.fallingRenderer = new FallingBrickRenderer(brickPanel, brickSize);
        this.ghostRenderer = new GhostBrickRenderer(boardPanel, brickSize);
        this.nextPieceRenderer = new NextPieceRenderer(nextPiecePanel);
    }

    // ---- Initialization ------------------------------------------------------

    public void initBoard(int[][] matrix) {
        boardRenderer.initBoard(matrix);
    }

    public void initFallingBrick(ViewData brick) {
        fallingRenderer.init(brick);
    }


    // ---- Rendering -----------------------------------------------------------

    public void renderBoard(int[][] matrix) {
        boardRenderer.refreshGameBackground(matrix);
    }

    public void renderFalling(ViewData brick) {
        fallingRenderer.render(brick);
    }

    public void renderGhost(ViewData brick, int[][] board) {
        try {
            ghostRenderer.renderGhost(brick, board);
        } catch (UnsupportedOperationException ignored) {}
    }

    public void renderNextPiece(ViewData next) {
        nextPieceRenderer.renderNextPiece(next.getNextBrickData());
    }

}
