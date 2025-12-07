package com.comp2042.Controllers;

import com.comp2042.*;
import com.comp2042.Infrastructure.Audio.MusicManager;
import com.comp2042.Infrastructure.HighScoreManager;
import com.comp2042.UI.*;
import com.comp2042.Inputs_Events.*;
import com.comp2042.UI.Renderers.RendererManager;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import com.comp2042.logic.core.ClearRow;
import com.comp2042.logic.core.ViewData;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Controller responsible for all GUI operations of the Tetris game.
 * Manages rendering of the board, falling bricks, next/held pieces,
 * scoreboard, level updates, notifications, input handling,
 * game loop timing, pause system, and scene navigation.
 * The GuiController acts as the bridge between the GameController
 * (game logic) and JavaFX UI components.
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    private static final int BRICK_Y_OFFSET = -42;
    private static final int INITIAL_DROP_DELAY = 400;

    /** Main game board gird where locked tiles are rendered*/
    @FXML private GridPane gamePanel;
    /** Grid responsible for rendering the falling brick*/
    @FXML private GridPane brickPanel;
    /**Panel showing the next piece preview*/
    @FXML private GridPane nextPiecePanel;
    /**Group used for floating notifications, score and level*/
    @FXML private Group groupNotification;
    /**Label displaying the current score*/
    @FXML private Label scoreLabel;
    /**Label displaying the high score*/
    @FXML private Label highscoreLabel;
    /**Label showing the current level*/
    @FXML private Label levelLabel;
    /**Label showing total cleared lines*/
    @FXML private Label linesLabel;
    /**Button used to pause or resume the game*/
    @FXML private Button pauseButton;
    /**Panel showing the held Tetromino*/
    @FXML private GridPane holdPiecePanel;


    // Core systems
    private RendererManager rendererManager;
    private NotificationManager notificationManager;
    private InputEventListener eventListener;
    private InputHandler inputHandler;
    private GameLoop gameLoop;
    private PauseManager pauseManager;
    private Main mainApp;

    /**
     * Called automatically by JavaFX after FXML loading.
     * Sets up fonts, input focus, and pause system.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFont();

        // focus
        gamePanel.setFocusTraversable(true);
        Platform.runLater(() -> gamePanel.requestFocus());

        // pause system
        pauseManager = new PauseManager(this::updatePauseButtonIcon);
    }

    /**
     * Injects the main application for scene switching.
     *
     * @param main the main application instance
     */
    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    /**
     * Starts the game by creating a new GameController instance.
     * GameController will call back into this GUI.
     */
    public void startGame() {
        // Main will create GameController externally
        new GameController(this);
    }

    /**
     * Initializes all visual elements: board, renderer, notifications,
     * next piece, and game loop.
     *
     * @param boardMatrix matrix representing the board background
     * @param brick initial falling brick view data
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {

        stopGameLoop();

        rendererManager = new RendererManager(gamePanel, brickPanel, nextPiecePanel, holdPiecePanel, BRICK_SIZE);

        rendererManager.initBoard(boardMatrix);
        rendererManager.renderFalling(brick);

        notificationManager = new NotificationManager(groupNotification);

        BrickPositioner.update(brickPanel, gamePanel, brick, BRICK_SIZE, BRICK_Y_OFFSET);
        highscoreLabel.setText(String.valueOf(HighScoreManager.loadHighScore()));
        gameLoop = new GameLoop(INITIAL_DROP_DELAY, e -> onMove(eventListener::onDownEvent, e));
        gameLoop.start();
    }

    /**
     * Registers the event listener (typically {@link GameController})
     * and installs keyboard input handlers.
     *
     * @param eventListener the listener for gameplay input events
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;

        inputHandler = new InputHandler(
                eventListener,
                this::refreshBrick,
                e -> onMove(eventListener::onDownEvent, e),
                e -> onMove(eventListener::onHardDropEvent, e),
                e -> onMove(eventListener::onHoldEvent, e)
        );

        gamePanel.setOnKeyPressed(inputHandler.getKeyHandler());
    }


    /**
     * Unified handler for movement actions such as soft drop or hard drop.
     *
     * @param handler function to apply movement
     * @param e       the move event
     */
    private void onMove(Function<MoveEvent, ViewData> handler, MoveEvent e) {
        if (pauseManager.isPaused()) return;

        ViewData view = handler.apply(e);
        if (view == null) return;

        showRowClear();
        refreshBrick(view);
        requestFocus();
    }

    /**
     * Displays row clear notification and sound effect.
     */
    private void showRowClear() {
        ClearRow row = eventListener.pollLastClearRow();
        if (row != null && row.getLinesRemoved() > 0) {
            MusicManager.getInstance().playSFX("bonus.mp3");
            notificationManager.showScoreNotification(row.getScoreBonus());
        }
    }

    /**
     * Refreshes the falling brick, ghost piece, and held piece rendering.
     *
     * @param brick current brick view data
     */
    private void refreshBrick(ViewData brick) {
        if (pauseManager.isPaused()) return;

        BrickPositioner.update(brickPanel, gamePanel, brick, BRICK_SIZE, BRICK_Y_OFFSET);

        rendererManager.renderFalling(brick);
        rendererManager.renderGhost(brick, eventListener.getBoardMatrix());
        rendererManager.renderHoldPiece(eventListener.getHeldBrickMatrix());

    }

    /**
     * Updates the board background after line clears or piece locks.
     *
     * @param board updated background matrix
     */
    public void refreshGameBackground(int[][] board) {
        rendererManager.renderBoard(board);
    }

    /**
     * Restarts the game while keeping the current scene.
     */
    @FXML
    private void restartGame() {
        MusicManager.getInstance().playSFX("button.mp3");
        stopGameLoop();
        eventListener.createNewGame();
        pauseManager.setPaused(false);
        gameLoop.start();
        requestFocus();
    }

    /**
     * Returns to the main menu scene.
     */
    @FXML
    private void handleStartMenu() {
        MusicManager.getInstance().playSFX("button.mp3");
        stopGameLoop();

        try {
            mainApp.showMainMenu();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Main Menu.").showAndWait();
        }
    }

    /**
     * Toggles pause state and updates game loop + button icon.
     */
    @FXML
    private void pauseGame() {
        MusicManager.getInstance().playSFX("button.mp3");
        pauseManager.togglePause(pauseButton);

        if (pauseManager.isPaused()) gameLoop.pause();
        else gameLoop.resume();

        requestFocus();
    }

    /**
     * Updates the pause/resume icon on the pause button.
     *
     * @param btn    the button to update
     * @param paused whether the game is paused
     */
    private void updatePauseButtonIcon(Button btn, boolean paused) {
        String img = paused ? "/images/resume.png" : "/images/pause.png";

        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(img)));
            icon.setFitWidth(28);
            icon.setFitHeight(28);
            icon.setPreserveRatio(true);
            btn.setGraphic(icon);
        } catch (Exception ignored) {}
    }

    /**
     * Displays the next Tetromino in the preview panel.
     *
     * @param next the next piece data
     */
    public void showNextPiece(ViewData next) {
        rendererManager.renderNextPiece(next);
    }

    /**
     * Shows a level-up notification animation.
     *
     * @param newLevel the new level number
     */
    public void showLevelUp(int newLevel) {
        notificationManager.showLevelUpNotification(newLevel);
    }


    /**
     * Adjusts the game loop speed based on the level.
     *
     * @param level the new level
     */
    public void setSpeedForLevel(int level) {
        gameLoop.setRate(1 + (level - 1) * 0.2);
    }

    /**
     * Handles game over: stops loop, saves high score, switches scene.
     */
    public void gameOver() {
        stopGameLoop();

        try {
            int score = Integer.parseInt(scoreLabel.getText());

            // SAVE HIGHSCORE
            HighScoreManager.saveHighScore(score);

            mainApp.showGameOverScreen(score);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Game Over screen.").showAndWait();
        }
    }


    /**
     * Binds the score property to the score label
     * and updates the high score in real time.
     *
     * @param score score property from GameController
     */
    public void bindScore(IntegerProperty score) {
        scoreLabel.textProperty().bind(score.asString("%d"));

        // LIVE high score update
        score.addListener((obs, oldVal, newVal) -> {
            int hs = HighScoreManager.loadHighScore();
            if (newVal.intValue() > hs) {
                highscoreLabel.setText(newVal.toString());
            }
        });
    }


    /**
     * Binds cleared lines to UI label.
     *
     * @param lines the property tracking cleared lines
     */
    public void bindLines(IntegerProperty lines) {
        linesLabel.textProperty().bind(lines.asString("%d"));
    }

    /**
     * Binds level property to UI label.
     *
     * @param level level property representing the current level
     */
    public void bindLevel(IntegerProperty level) {
        levelLabel.textProperty().bind(level.asString("%d"));
    }

    /**
     * Stops the game loop safely.
     */
    private void stopGameLoop() {
        if (gameLoop != null) gameLoop.stop();
    }

    /**
     * Requests keyboard focus on the game panel.
     */
    private void requestFocus() {
        Platform.runLater(() -> gamePanel.requestFocus());
    }

    /**
     * Loads custom Tetris digital font.
     */
    private void loadFont() {
        try {
            Font.loadFont(getClass().getClassLoader()
                    .getResource("digital.ttf").toExternalForm(), 38);
        } catch (Exception ignored) {}
    }
}
