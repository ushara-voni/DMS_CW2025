package com.comp2042.Controllers;

import com.comp2042.Infrastructure.Audio.MusicManager;
import com.comp2042.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the Main Menu screen.
 * Handles user interactions such as starting the game,
 * opening the instructions page, and exiting the application.
 */
public class MainMenuController {

    /**
     * Reference to the main application.
     * Used to trigger screen transitions.
     */
    private Main mainApp;

    @FXML
    private Button startButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button exitButton;

    /**
     * Injects the main application instance into this controller.
     * This allows the controller to request scene changes
     * such as starting the game or opening instruction screens.
     *
     * @param mainApp the main application
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    /**
     * Handles the event when the user clicks the "Start Game" button.
     * Plays a sound effect and transitions to the main game screen.
     *
     * @param event the action event triggered by clicking the button
     * @throws Exception if the game screen cannot be loaded
     */
    @FXML
    private void handleStartGame(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showGameScreen();
    }

    /**
     * Handles the event when the user clicks the "Instructions" button.
     * Plays a sound effect and opens the instructions screen.
     *
     * @throws Exception if the instructions screen fails to load
     */
    @FXML
    public void handleInstructions() throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showInstructions();
    }

    /**
     * Handles the event when the user clicks the "Exit" button.
     * Plays a sound effect and closes the application.
     *
     * @param event the triggered action event
     */
    @FXML
    private void handleExit(ActionEvent event) {
        MusicManager.playSFX("button.mp3");
        System.exit(0);
    }


    /**
     * Initializes the controller.
     * Automatically plays background music when the main menu loads.
     */
    public void initialize() {
        // Play main menu BGM
        MusicManager.playBGM("bgm.mp3");
    }

}
