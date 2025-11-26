package com.comp2042.Controllers;

import com.comp2042.Audio.MusicManager;
import com.comp2042.UI.HighScoreManager;
import com.comp2042.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class GameOverController {

    private Main mainApp;

    @FXML
    private Label finalScoreLabel;

    @FXML
    private Button restartButton;

    @FXML
    private Button backMenuButton;

    @FXML
    private Button exitButton;

    @FXML private Label highScoreLabel;

    private int finalScore;


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setFinalScore(int score) {
        this.finalScore = score;

        // Show final score on the label
        finalScoreLabel.setText("Score: " + score);

        // Save high score if this is a new one
        HighScoreManager.saveHighScore(score);

        // Load high score and display
        int highScore = HighScoreManager.loadHighScore();
        highScoreLabel.setText("High Score: " + highScore);
    }
    @FXML
    private void handleRestart(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showGameScreen();
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showMainMenu();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        MusicManager.playSFX("button.mp3");
        System.exit(0);
    }
}
