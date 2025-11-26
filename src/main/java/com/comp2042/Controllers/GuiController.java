package com.comp2042.Controllers;

import com.comp2042.*;
import com.comp2042.Inputs_Events.*;
import com.comp2042.Renderers.RendererManager;
import com.comp2042.UI.ClearRow;
import com.comp2042.UI.NotificationManager;
import com.comp2042.UI.ViewData;
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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    private static final int BRICK_Y_OFFSET = -42;
    private static final int INITIAL_DROP_DELAY = 400;

    // FXML
    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private GridPane nextPiecePanel;
    @FXML private Group groupNotification;

    @FXML private Label scoreLabel;
    @FXML private Label levelLabel;
    @FXML private Label linesLabel;

    @FXML private Button pauseButton;
    @FXML private GridPane holdPiecePanel;


    // Core collaborators
    private RendererManager rendererManager;
    private NotificationManager notificationManager;
    private InputEventListener eventListener;
    private InputHandler inputHandler;
    private GameLoop gameLoop;
    private PauseManager pauseManager;
    private Main mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFont();

        // focus
        gamePanel.setFocusTraversable(true);
        Platform.runLater(() -> gamePanel.requestFocus());

        // pause system
        pauseManager = new PauseManager(this::updatePauseButtonIcon);
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    public void startGame() {
        // Main will create GameController externally
        new GameController(this);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {

        stopGameLoop();

        rendererManager = new RendererManager(gamePanel, brickPanel, nextPiecePanel, holdPiecePanel, BRICK_SIZE);

        rendererManager.initBoard(boardMatrix);
        rendererManager.initFallingBrick(brick);

        notificationManager = new NotificationManager(groupNotification);

        BrickPositioner.update(brickPanel, gamePanel, brick, BRICK_SIZE, BRICK_Y_OFFSET);

        gameLoop = new GameLoop(INITIAL_DROP_DELAY, e -> onMove(eventListener::onDownEvent, e));
        gameLoop.start();
    }

    /** EVENT LISTENER + INPUT SETUP **/
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

    /** MOVEMENT — unified handling for down / hard drop */
    private void onMove(Function<MoveEvent, ViewData> handler, MoveEvent e) {
        if (pauseManager.isPaused()) return;

        ViewData view = handler.apply(e);
        if (view == null) return;

        showRowClear();
        refreshBrick(view);
        requestFocus();
    }

    /** ROW CLEAR */
    private void showRowClear() {
        ClearRow row = eventListener.pollLastClearRow();
        if (row != null && row.getLinesRemoved() > 0) {
            notificationManager.showScoreNotification(row.getScoreBonus());
        }
    }

    /** BRICK REFRESH */
    private void refreshBrick(ViewData brick) {
        if (pauseManager.isPaused()) return;

        BrickPositioner.update(brickPanel, gamePanel, brick, BRICK_SIZE, BRICK_Y_OFFSET);

        rendererManager.renderFalling(brick);
        rendererManager.renderGhost(brick, eventListener.getBoardMatrix());
        rendererManager.renderHoldPiece(eventListener.getHeldBrickMatrix());
    }

    /** BACKGROUND UPDATE */
    public void refreshGameBackground(int[][] board) {
        rendererManager.renderBoard(board);
    }

    /** BUTTON ACTIONS **/
    @FXML
    private void restartGame() {
        stopGameLoop();
        eventListener.createNewGame();
        pauseManager.setPaused(false);
        gameLoop.start();
        requestFocus();
    }

    @FXML
    private void handleStartMenu() {
        stopGameLoop();

        try {
            mainApp.showMainMenu();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Main Menu.").showAndWait();
        }
    }

    @FXML
    private void pauseGame() {
        pauseManager.togglePause(pauseButton);

        if (pauseManager.isPaused()) gameLoop.pause();
        else gameLoop.resume();

        requestFocus();
    }

    /** PAUSE ICON UPDATE */
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

    /** LEVEL / SPEED / NEXT PIECE */
    public void showNextPiece(ViewData next) {
        rendererManager.renderNextPiece(next);
    }

    public void showLevelUp(int newLevel) {
        notificationManager.showLevelUpNotification(newLevel);
    }

    public void setSpeedForLevel(int level) {
        gameLoop.setRate(1 + (level - 1) * 0.2);
    }

    /** GAME OVER */
    public void gameOver() {
        stopGameLoop();

        try {
            int score = Integer.parseInt(scoreLabel.getText());
            mainApp.showGameOverScreen(score);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Game Over screen.").showAndWait();
        }
    }

    /** BINDINGS **/
    public void bindScore(IntegerProperty score) {
        scoreLabel.textProperty().bind(score.asString("%d"));
    }

    public void bindLines(IntegerProperty lines) {
        linesLabel.textProperty().bind(lines.asString("%d"));
    }

    public void bindLevel(IntegerProperty level) {
        levelLabel.textProperty().bind(level.asString("%d"));
    }

    /** UTILITY **/
    private void stopGameLoop() {
        if (gameLoop != null) gameLoop.stop();
    }

    private void requestFocus() {
        Platform.runLater(() -> gamePanel.requestFocus());
    }

    private void loadFont() {
        try {
            Font.loadFont(getClass().getClassLoader()
                    .getResource("digital.ttf").toExternalForm(), 38);
        } catch (Exception ignored) {}
    }
}
