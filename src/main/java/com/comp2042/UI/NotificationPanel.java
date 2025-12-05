package com.comp2042.UI;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Represents a pop-up notification panel for Tetris gameplay.
 * Can display score bonuses or level-up messages with animations.
 */
public class NotificationPanel extends BorderPane {

    /**
     * Constructs a notification panel with the specified text.
     * Applies default styling and glow effect.
     *
     * @param text the text to display on the panel (e.g., "+100" or "LEVEL 2")
     */
    public NotificationPanel(String text) {
        setMinHeight(200);
        setMinWidth(220);
        final Label score = new Label(text);
        score.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);
        setCenter(score);

    }

    /**
     * Shows a score notification with fade and upward translation animations.
     * Removes the panel from the provided node list after the animation completes.
     *
     * @param list the list of nodes (typically the notification group) to which this panel belongs
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        setLayoutY(50);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }

    /**
     * Shows a level-up notification with specialized styling and animations.
     * Centers the notification and removes it from the node list after animation.
     *
     * @param list the list of nodes (typically the notification group) to which this panel belongs
     */
    public void showLevel(ObservableList<Node> list) {
        // Apply special styling for LEVEL
        Label label = (Label) getCenter();
        label.setStyle(
                "-fx-font-size: 90px;" +
                        "-fx-font-weight: normal;"+
                "-fx-text-fill: gold;" +
                        "-fx-effect: dropshadow(gaussian, gold, 20, 0.8, 0, 0);"
        );

        // Optional: center the LEVEL UP popup horizontally
        setLayoutX((getParent().getLayoutBounds().getWidth() - getWidth()) / 2);
        setLayoutY(-50); // slightly higher than score popups

        // Use same animation as showScore
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);

        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }

}
