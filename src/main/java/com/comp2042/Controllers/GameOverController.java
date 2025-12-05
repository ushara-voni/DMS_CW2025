package com.comp2042.Controllers;

import com.comp2042.Infrastructure.Audio.MusicManager;
import com.comp2042.Infrastructure.HighScoreManager;
import com.comp2042.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
/**
 * Controller for the Game Over screen.
 * Displays the player's final score, updates and shows the high score,
 * and handles navigation actions such as restarting the game, returning
 * to the main menu, or exiting the application.
 */
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

    /**
     * Sets the reference to the main application.
     *
     * @param mainApp the main application instance
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the final score displayed on the Game Over screen.
     * Also saves and updates the high score.
     *
     * @param score the player's final score
     */
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

    /**
     * Handles the restart button action.
     * Plays a sound effect and starts a new game session.
     *
     * @param event the button event
     * @throws Exception if the game screen fails to load
     */
    @FXML
    private void handleRestart(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showGameScreen();
    }

    /**
     * Handles the return-to-menu action.
     * Plays a sound effect and navigates back to the main menu.
     *
     * @param event the button event
     * @throws Exception if the main menu fails to load
     */
    @FXML
    private void handleBackToMenu(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showMainMenu();
    }

    /**
     * Handles the exit button action.
     * Plays a sound effect and closes the application.
     *
     * @param event the button event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        MusicManager.playSFX("button.mp3");
        System.exit(0);
    }
}
