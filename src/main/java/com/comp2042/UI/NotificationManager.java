package com.comp2042.UI;

import javafx.scene.Group;

/**
 * Manages and displays in-game notifications such as score increments and level-ups.
 * Uses NotificationPanel instances to visually present notifications.
 */
public class NotificationManager {
    /** Group node used to contain and display notification panels. */
    private final Group notificationGroup;

    /**
     * Constructs a NotificationManager for the specified Group.
     *
     * @param notificationGroup the JavaFX Group where notifications will be added
     */
    public NotificationManager(Group notificationGroup) {
        this.notificationGroup = notificationGroup;
    }

    /**
     * Shows a score notification, e.g., "+100", on the notification group.
     *
     * @param score the score increment to display
     */
    public void showScoreNotification(int score) {

        NotificationPanel panel = new NotificationPanel("+" + score);
        notificationGroup.getChildren().add(panel);
        panel.showScore(notificationGroup.getChildren());
    }

    /**
     * Shows a level-up notification, e.g., "LEVEL 2", on the notification group.
     *
     * @param level the new player level to display
     */
    public void showLevelUpNotification(int level) {
        NotificationPanel panel = new NotificationPanel("LEVEL " + level);
        notificationGroup.getChildren().add(panel);
        panel.showLevel(notificationGroup.getChildren());
    }
}
