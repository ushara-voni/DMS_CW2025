package com.comp2042;

import com.comp2042.Controllers.GameOverController;
import com.comp2042.Controllers.GuiController;
import com.comp2042.Controllers.InstructionsController;
import com.comp2042.Controllers.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Tetris game application.
 * <p>
 * This class initializes the JavaFX application, manages the primary stage,
 * and handles switching between different screens: main menu, game screen,
 * game over screen, and instructions.
 * </p>
 */
public class Main extends Application {


    /** The primary stage of the application. */
    private Stage primaryStage;

    /**
     * Starts the JavaFX application.
     * Initializes the primary stage with width, height, and title,
     * shows the main menu, and disables resizing.
     *
     * @param stage the primary stage provided by JavaFX
     * @throws Exception if loading FXML fails
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        primaryStage.setWidth(530);   // width in pixels
        primaryStage.setHeight(780);  // height in pixels
        primaryStage.setTitle("Tetris");
        showMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Shows the main menu screen.
     *
     * @throws Exception if loading the FXML fails
     */
    public void showMainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        MainMenuController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
    }

    /**
     * Shows the game screen and starts the Tetris game.
     *
     * @throws Exception if loading the FXML fails
     */
    public void showGameScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameLayout.fxml"));
        Scene scene = new Scene(loader.load());
        GuiController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        controller.startGame();
    }

    /**
     * Shows the game over screen with the player's final score.
     *
     * @param finalScore the final score achieved by the player
     * @throws Exception if loading the FXML fails
     */
    public void showGameOverScreen(int finalScore) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameOverScreen.fxml"));
        Scene scene = new Scene(loader.load());
        GameOverController controller = loader.getController();
        controller.setMainApp(this);
        controller.setFinalScore(finalScore);
        primaryStage.setScene(scene);
    }
    /**
     * Shows the instructions screen.
     *
     * @throws Exception if loading the FXML fails
     */
    public void showInstructions() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Instructions.fxml"));
        Scene scene = new Scene(loader.load());

        // Optional: if you want to pass a reference to Main to the controller
        InstructionsController controller = loader.getController();
        controller.setMainApp(this); // only if your InstructionsController has this method

        primaryStage.setScene(scene);
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
