package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;


    @FXML
    private Label scoreLabel;




    @FXML
    private Button pauseButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button restartButton;

    @FXML
    private GridPane nextPiecePanel;


    @FXML
    private Label linesLabel;


    @FXML
    private void restartGame(ActionEvent event){
        newGame(null);

    }


    @FXML
    private void handleStartMenu() {
        if (mainApp != null) {
            try {
                mainApp.showMainMenu();
            } catch (Exception e) {
                e.printStackTrace();
                // Optional: alert the user if loading fails
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Main Menu.");
                alert.showAndWait();
            }
        }
    }



    public void bindScore(IntegerProperty scoreProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(scoreProperty.asString("%d"));
        }
    }

    public void bindLines(IntegerProperty linesProperty) {
        if (linesLabel != null) {
            linesLabel.textProperty().bind(linesProperty.asString("%d"));
        }
    }


    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private GameController gameController;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private Main mainApp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        // Disable focus on all menu and control buttons
        Button[] buttons = {  pauseButton, restartButton, homeButton};
        for (Button b : buttons) {
            if (b != null) b.setFocusTraversable(false);
        }


        brickPanel.setFocusTraversable(false);
        gamePanel.setFocusTraversable(true);
        gamePanel.setOnMouseClicked(e -> gamePanel.requestFocus());
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });


        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        if (timeLine != null) {
            timeLine.stop(); // stop any old game timeline
        }
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                //change
                rectangle.getStyleClass().add("cell-style");
                //change
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        //change
        brickPanel.toFront();



        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                //change
                rectangle.getStyleClass().addAll("cell-style", "brick");
                //change
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            //change
            brickPanel.toFront();
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }





    public void newGame(ActionEvent actionEvent) {
        if (timeLine != null) {
            timeLine.stop();
        }

        eventListener.createNewGame();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        timeLine.play();

        Platform.runLater(() -> gamePanel.requestFocus());
    }



    public void pauseGame(ActionEvent actionEvent) {
        if(isPause.getValue()){
            timeLine.play();
            isPause.setValue(false);
            if(actionEvent != null){
                ((Button) actionEvent.getSource()).setText("Pause");
            }
            gamePanel.requestFocus();
        }else{
            timeLine.pause();
            isPause.setValue(true);
            ((Button) actionEvent.getSource()).setText("Resume");
        }


    }

    public void showNextPiece(ViewData viewData) {
        nextPiecePanel.getChildren().clear(); // clear previous preview
        int[][] nextBrick = viewData.getNextBrickData();

        for (int i = 0; i < nextBrick.length; i++) {
            for (int j = 0; j < nextBrick[i].length; j++) {
                if (nextBrick[i][j] != 0) {
                    Rectangle rectangle = new Rectangle(18, 18); // smaller block for preview
                    rectangle.setFill(getFillColor(nextBrick[i][j]));
                    rectangle.setArcWidth(6);
                    rectangle.setArcHeight(6);
                    nextPiecePanel.add(rectangle, j, i);
                }
            }
        }
    }




    public void setMainApp(Main main) {
        this.mainApp = main;
    }

    public void startGame() {
        if (gameController != null) {
            gameController.stopGame();
        }
        gameController = new GameController(this);
    }

    public void gameOver() {
        timeLine.stop();
        isGameOver.set(true);
        if (mainApp != null) {
            try {
                mainApp.showGameOverScreen(Integer.parseInt(scoreLabel.getText()));
            } catch (Exception e) {
                e.printStackTrace();
                // Optional: show an alert if loading fails
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Game Over screen.");
                alert.showAndWait();
            }
        }
    }






}
