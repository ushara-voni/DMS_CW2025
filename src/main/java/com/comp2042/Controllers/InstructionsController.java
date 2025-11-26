package com.comp2042.Controllers;

import com.comp2042.Audio.MusicManager;
import com.comp2042.Main;
import javafx.fxml.FXML;

public class InstructionsController {

    private Main mainApp;

    // Setter to allow Main to pass itself to this controller
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    // Called when the "Back to Menu" button is clicked
    @FXML
    private void handleBackToMenu() {
        try {
            if (mainApp != null) {
                MusicManager.playSFX("button.mp3");
                mainApp.showMainMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

