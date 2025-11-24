package com.comp2042.Controllers;

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


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setFinalScore(int score) {
        finalScoreLabel.setText("Score: " + score);
    }

    @FXML
    private void handleRestart(ActionEvent event) throws Exception {
        mainApp.showGameScreen();
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) throws Exception {
        mainApp.showMainMenu();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
}
