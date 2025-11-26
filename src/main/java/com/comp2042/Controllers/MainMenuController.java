package com.comp2042.Controllers;

import com.comp2042.Audio.MusicManager;
import com.comp2042.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    private Main mainApp;

    @FXML
    private Button startButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button exitButton;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleStartGame(ActionEvent event) throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showGameScreen();
    }

    @FXML
    public void handleInstructions() throws Exception {
        MusicManager.playSFX("button.mp3");
        mainApp.showInstructions();
    }


    @FXML
    private void handleExit(ActionEvent event) {
        MusicManager.playSFX("button.mp3");
        System.exit(0);
    }

    public void initialize() {
        // Play main menu BGM
        MusicManager.playBGM("bgm.mp3");
    }

}
