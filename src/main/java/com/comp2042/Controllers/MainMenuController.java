package com.comp2042.Controllers;

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
        mainApp.showGameScreen();
    }

    @FXML
    public void handleInstructions() throws Exception {
        mainApp.showInstructions();
    }


    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }
}
