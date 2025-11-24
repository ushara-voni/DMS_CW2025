package com.comp2042.Controllers;

import com.comp2042.Inputs_Events.InputEventListener;
import com.comp2042.Inputs_Events.MoveEvent;
import com.comp2042.UI.Board;
import com.comp2042.UI.ClearRow;
import com.comp2042.UI.TetrisBoard;
import com.comp2042.UI.ViewData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameController implements InputEventListener {

    private final Board board = new TetrisBoard(33, 15);

    private final GuiController viewGuiController;
    private final SimpleIntegerProperty linesCleared = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty level = new SimpleIntegerProperty(1);

    private static final int LINES_PER_LEVEL = 10;
    private ClearRow lastClearRow = null;

    public GameController(GuiController gui) {
        viewGuiController = gui;

        // initial game setup
        board.createNewBrick();

        // wire view <-> controller
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.showNextPiece(board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLines(linesClearedProperty());
        viewGuiController.bindLevel(levelProperty());
    }

    // Merge locked brick, clear rows, spawn new, refresh view
    private ViewData lockBrickAndSpawnNew() {
        board.mergeBrickToBackground();

        ClearRow clearRow = board.clearRows();
        updateScoreAndLevel(clearRow);

        // create new brick BEFORE refreshing the view so the UI shows the correct next state
        boolean gameOver = !board.createNewBrick();

        // Refresh the view immediately after state updates (so bindings reflect new values)
        refreshView();

        if (gameOver) {
            // Ask GUI to stop its loop + show game over screen
            viewGuiController.gameOver();
        }

        return board.getViewData();
    }

    private void updateScoreAndLevel(ClearRow clearRow) {
        if (clearRow != null && clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
            linesCleared.set(linesCleared.get() + clearRow.getLinesRemoved());
            checkLevelUp();
            lastClearRow = clearRow;
        } else {
            lastClearRow = null;
        }
    }

    @Override
    public ClearRow pollLastClearRow() {
        ClearRow temp = lastClearRow;
        lastClearRow = null;
        return temp;
    }

    private void refreshView() {
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.showNextPiece(board.getViewData());
    }

    // Input handlers
    @Override
    public ViewData onDownEvent(MoveEvent event) {
        if (!board.moveBrickDown()) {
            return lockBrickAndSpawnNew();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onHardDropEvent(MoveEvent event) {
        while (board.moveBrickDown()) {}
        return lockBrickAndSpawnNew();
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        linesCleared.set(0);
        level.set(1);
        refreshView();
    }

    // Level & line tracking
    public IntegerProperty linesClearedProperty() {
        return linesCleared;
    }

    private void checkLevelUp() {
        int currentLevel = level.get();
        int cleared = linesCleared.get();

        if (cleared >= currentLevel * LINES_PER_LEVEL) {
            level.set(currentLevel + 1);
            viewGuiController.showLevelUp(level.get());
            increaseSpeed();
        }
    }

    private void increaseSpeed() {
        viewGuiController.setSpeedForLevel(level.get());
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }
}
