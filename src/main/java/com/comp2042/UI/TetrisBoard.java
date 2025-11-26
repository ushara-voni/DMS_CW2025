package com.comp2042.UI;

public class TetrisBoard implements Board {

    private final BoardGrid boardGrid;
    private final BrickManager brickManager;
    private final Score gameScore;

    public TetrisBoard(int width, int height) {
        boardGrid = new BoardGrid(width, height);
        brickManager = new BrickManager(boardGrid);
        gameScore = new Score();
        brickManager.spawnNewBrick();
    }

    // --- Brick movement ---
    @Override public boolean moveBrickDown() { return brickManager.moveDown(); }
    @Override public boolean moveBrickLeft() { return brickManager.moveLeft(); }
    @Override public boolean moveBrickRight() { return brickManager.moveRight(); }
    @Override public boolean rotateLeftBrick() { return brickManager.rotateLeft(); }
    @Override public boolean createNewBrick() { return brickManager.spawnNewBrick(); }

    // --- Board state ---
    @Override public int[][] getBoardMatrix() { return boardGrid.getMatrixCopy(); }
    @Override public void mergeBrickToBackground() { brickManager.mergeToBoard(); }
    @Override public ClearRow clearRows() { return boardGrid.clearRows(); }

    // --- Score ---
    @Override public Score getScore() { return gameScore; }

    // --- View data ---
    @Override public ViewData getViewData() { return brickManager.getViewData(); }

    // --- New game ---
    @Override
    public void newGame() {
        boardGrid.reset();
        gameScore.reset();
        brickManager.spawnNewBrick();
    }

    @Override
    public ViewData holdBrick() {
        return brickManager.holdPiece();
    }

    @Override
    public int[][] getHeldBrickMatrix() {
        return brickManager.getHeldBrickMatrix();
    }

}
