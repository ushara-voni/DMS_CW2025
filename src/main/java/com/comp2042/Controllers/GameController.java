package com.comp2042.Controllers;

import com.comp2042.Infrastructure.Audio.MusicManager;
import com.comp2042.Inputs_Events.InputEventListener;
import com.comp2042.Inputs_Events.MoveEvent;
import com.comp2042.logic.core.Board;
import com.comp2042.logic.core.ClearRow;
import com.comp2042.logic.core.TetrisBoard;
import com.comp2042.logic.core.ViewData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * The central game controller responsible for coordinating player input,
 * game state updates, scoring, level progression, and communication with
 * the GUI layer.
 * This class listens for movement and gameplay actions from the UI,
 * updates the underlying {@link Board} model, and forwards rendered
 * state information to the {@link GuiController}.
 * The controller also manages background music, level increases,
 * line clearing events, and game-over flow.
 */
public class GameController implements InputEventListener {
    /** Underlying game board and brick logic. */
    private final Board board = new TetrisBoard(15, 33);
    /** GUI controller used to update and display game state. */
    private final GuiController viewGuiController;
    /** Tracks total number of cleared lines across the session. */
    private final SimpleIntegerProperty linesCleared = new SimpleIntegerProperty(0);
    /** Tracks the player's current level. */
    private final SimpleIntegerProperty level = new SimpleIntegerProperty(1);
    /** Number of lines required to advance one level. */
    private static final int LINES_PER_LEVEL = 5;
    /** Cached clear-row event so GUI can poll it for animations. */
    private ClearRow lastClearRow = null;

    /**
     * Constructs the game controller and performs initial setup:
     * <ul>
     *   <li>Starts the gameplay background music</li>
     *   <li>Creates the first falling brick</li>
     *   <li>Binds UI elements to score, level, and line counters</li>
     *   <li>Initializes the game board and next-piece preview in the GUI</li>
     * </ul>
     *
     * @param gui the GUI controller that handles rendering and input routing
     */
    public GameController(GuiController gui) {
        viewGuiController = gui;
        MusicManager.getInstance().playBGM("gameplay.mp3");

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

    /**
     * Locks the current falling brick into the board background, clears rows,
     * updates the score/level, spawns a new brick, refreshes the UI, and checks
     * for game-over conditions.
     *
     * @return the updated view data after spawning a new brick
     */
    private ViewData lockBrickAndSpawnNew() {
        board.mergeBrickToBackground();

        ClearRow clearRow = board.clearRows();
        updateScoreAndLevel(clearRow);

        // create new brick BEFORE refreshing the view so the UI shows the correct next state
        boolean gameOver = !board.createNewBrick();

        // Refresh the view immediately after state updates (so bindings reflect new values)
        refreshView();

        if (gameOver) {
            MusicManager.getInstance().playSFX("game-over-sfx.mp3");
            MusicManager.getInstance().playBGM("game_over.mp3");
            // Ask GUI to stop its loop + show game over screen
            viewGuiController.gameOver();
        }

        return board.getViewData();
    }
    /**
     * Updates the player's score and level based on the provided {@link ClearRow}.
     * If rows were cleared, adds score bonuses, increments the lines cleared,
     * and triggers level-up checks.
     *
     * @param clearRow the {@link ClearRow} object representing cleared rows,
     *                 or {@code null} if no rows were cleared
     */
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
    /**
     * Returns the last row-clear event and clears the stored reference.
     * Used by the GUI to animate row removal.
     *
     * @return the last {@link ClearRow} event, or {@code null} if none
     */
    @Override
    public ClearRow pollLastClearRow() {
        ClearRow temp = lastClearRow;
        lastClearRow = null;
        return temp;
    }
    /**
     * Refreshes the game board and next-piece preview in the GUI.
     * Updates both the locked blocks and the current falling brick.
     */
    private void refreshView() {
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.showNextPiece(board.getViewData());

    }

    /**
     * Handles a soft-drop input event (Move Down). If the brick cannot move down
     * further, it will be locked into place and a new brick will be spawned.
     *
     * @param event the {@link MoveEvent} triggering the soft drop (ignored)
     * @return the updated {@link ViewData} after the move
     */
    @Override
    public ViewData onDownEvent(MoveEvent event) {
        if (!board.moveBrickDown()) {
            return lockBrickAndSpawnNew();
        }
        return board.getViewData();
    }

    /**
     * Handles a hard-drop input event by moving the current brick down to the
     * lowest possible valid position and locking it.
     *
     * @param event the {@link MoveEvent} triggering the hard drop (ignored)
     * @return the updated {@link ViewData} after the brick is locked and a new
     *         brick is spawned
     */
    @Override
    public ViewData onHardDropEvent(MoveEvent event) {
        while (board.moveBrickDown()) {}
        return lockBrickAndSpawnNew();
    }


    /**
     * Moves the current brick one cell to the left.
     *
     * @param event the {@link MoveEvent} triggering the move (ignored)
     * @return the updated {@link ViewData} after the move
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Moves the current brick one cell to the right.
     *
     * @param event the {@link MoveEvent} triggering the move (ignored)
     * @return the updated {@link ViewData} after the move
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }


    /**
     * Rotates the current brick to the left (counter-clockwise).
     *
     * @param event the {@link MoveEvent} triggering the rotation (ignored)
     * @return the updated {@link ViewData} after rotation
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Starts a brand new game by resetting the board, score, lines, level,
     * and restarting background music.
     */
    @Override
    public void createNewGame() {
        MusicManager.getInstance().playBGM("gameplay.mp3");
        board.newGame();
        linesCleared.set(0);
        level.set(1);
        refreshView();
    }

    /**
     * Returns the total lines cleared
     *
     * @return observable property containing total lines cleared
     */
    public IntegerProperty linesClearedProperty() {
        return linesCleared;
    }

    /**
     * Checks whether the player has cleared enough lines to level up. If so,
     * increments the level, updates the GUI, and increases drop speed.
     */
    private void checkLevelUp() {
        int currentLevel = level.get();
        int cleared = linesCleared.get();

        if (cleared >= currentLevel * LINES_PER_LEVEL) {
            level.set(currentLevel + 1);
            viewGuiController.showLevelUp(level.get());
            increaseSpeed();
        }
    }

    /**
     * Increases the game's drop speed according to the current level.
     */
    private void increaseSpeed() {
        viewGuiController.setSpeedForLevel(level.get());
    }

    /**
     * Returns the player's current level
     *
     * @return observable level property
     */
    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * Returns the board matrix for rendering or debugging.
     *
     * @return a 2D integer matrix representing the board contents
     */
    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    /**
     * Handles the hold-piece action.
     *
     * @param event the {@link MoveEvent} triggering the hold (ignored)
     * @return updated view data after hold action
     */
    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        return board.holdBrick();
    }

    /**
     * Returns the matrix for the currently held brick.
     *
     * @return the held brick's 2D matrix, or {@code null} if none held
     */

    @Override
    public int[][] getHeldBrickMatrix() {
        return board.getHeldBrickMatrix();
    }



}
