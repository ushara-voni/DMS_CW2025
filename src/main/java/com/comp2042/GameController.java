package com.comp2042;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;



public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(33, 15);

    private final GuiController viewGuiController;
    private final IntegerProperty linesCleared= new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(1);
    private Timeline timeLine;
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        //change
        viewGuiController.showNextPiece(board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLines(linesClearedProperty());
        viewGuiController.bindLevel(levelProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                linesCleared.set(linesCleared.get() + clearRow.getLinesRemoved());
                checkLevelUp();
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            //change
            viewGuiController.showNextPiece(board.getViewData());
        }
        return new DownData(clearRow, board.getViewData());
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
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }

    public void stopGame() {
        if (timeLine != null) {
            timeLine.stop();
        }
    }


    public IntegerProperty linesClearedProperty(){
        return linesCleared;
    }


    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        // keep moving down until it cannot
        while (board.moveBrickDown()) {
            // keep falling
        }

        // Now lock piece
        board.mergeBrickToBackground();

        // Clear full lines
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
            linesCleared.set(linesCleared.get() + clearRow.getLinesRemoved());
            checkLevelUp();
        }

        // spawn next
        if (board.createNewBrick()) {
            viewGuiController.gameOver();
        }

        // refresh background + next piece
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.showNextPiece(board.getViewData());

        // return what the GUI needs
        return new DownData(clearRow, board.getViewData());
    }

    private void checkLevelUp(){
        int currentLevel= level.get();
        int cleared = linesCleared.get();

        if(cleared >=currentLevel * 10 ){
            level.set(currentLevel+ 1);
            viewGuiController.showLevelUp(level.get());
            increaseSpeed();
        }
    }

    private void increaseSpeed(){
        viewGuiController.setSpeedForLevel(level.get());
    }

    public IntegerProperty levelProperty() {
        return level;
    }



}


