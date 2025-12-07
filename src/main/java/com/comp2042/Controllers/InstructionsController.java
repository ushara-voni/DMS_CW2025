package com.comp2042.Controllers;

import com.comp2042.Infrastructure.Audio.MusicManager;
import com.comp2042.Main;
import javafx.fxml.FXML;

/**
 * Controller class for the Instructions screen.
 * This class manages user interactions on the Instructions view,
 * such as navigating back to the main menu.
 */
public class InstructionsController {

    /**
     * Reference to the main application.
     * Used for switching scenes.
     */
    private Main mainApp;


    /**
     * Sets the main application reference.
     * This method is called by the {@code Main} class after the FXML
     * controller is created, allowing the controller to request scene changes.
     *
     * @param mainApp the main application instance
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Handles the event triggered when the user clicks
     * the "Back to Menu" button.
     * Plays a button sound effect and navigates back to the main menu.
     */
    @FXML
    private void handleBackToMenu() {
        try {
            if (mainApp != null) {
                MusicManager.getInstance().playSFX("button.mp3");
                mainApp.showMainMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

