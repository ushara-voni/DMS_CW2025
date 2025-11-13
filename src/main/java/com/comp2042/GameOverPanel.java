//package com.comp2042;
//
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//
//
//public class GameOverPanel extends BorderPane {
//
//    public GameOverPanel() {
//        final Label gameOverLabel = new Label("GAME OVER");
//        gameOverLabel.getStyleClass().add("gameOverStyle");
//        setCenter(gameOverLabel);
//    }
//
//}

package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameOverPanel extends BorderPane {
    private final Label scoreLabel;
    private final Button restartButton;
    private final Button homeButton;

    public GameOverPanel() {
        // --- Title ---
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        // --- Score Label ---
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");

        // --- Buttons (horizontal layout) ---
        restartButton = new Button("Restart");
        homeButton = new Button("Home");

        restartButton.setStyle("-fx-font-size: 18px; -fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-background-radius: 10;");
        homeButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4caf50; -fx-text-fill: white; -fx-background-radius: 10;");

        HBox buttonBox = new HBox(20, restartButton, homeButton);
        buttonBox.setAlignment(Pos.CENTER);

        // --- Stack elements vertically (title, score, buttons) ---
        VBox content = new VBox(20, gameOverLabel, scoreLabel, buttonBox);
        content.setAlignment(Pos.CENTER);

        setCenter(content);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 40px;");

        setVisible(false); // Start hidden until game over
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }
}