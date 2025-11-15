package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        primaryStage.setWidth(530);   // width in pixels
        primaryStage.setHeight(780);  // height in pixels
        primaryStage.setTitle("Tetris");
        showMainMenu();
        primaryStage.show();
    }

    public void showMainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        MainMenuController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
    }

    public void showGameScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameLayout.fxml"));
        Scene scene = new Scene(loader.load());
        GuiController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        controller.startGame();
    }

    public void showGameOverScreen(int finalScore) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameOverScreen.fxml"));
        Scene scene = new Scene(loader.load());
        GameOverController controller = loader.getController();
        controller.setMainApp(this);
        controller.setFinalScore(finalScore);
        primaryStage.setScene(scene);
    }
    public void showInstructions() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Instructions.fxml"));
        Scene scene = new Scene(loader.load());

        // Optional: if you want to pass a reference to Main to the controller
        InstructionsController controller = loader.getController();
        controller.setMainApp(this); // only if your InstructionsController has this method

        primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
