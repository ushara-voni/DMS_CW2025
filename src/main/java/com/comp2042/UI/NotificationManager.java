package com.comp2042.UI;

import javafx.scene.Group;

public class NotificationManager {

    private final Group notificationGroup;

    public NotificationManager(Group notificationGroup) {
        this.notificationGroup = notificationGroup;
    }

    /** Show a score notification (e.g., "+100") */
    public void showScoreNotification(int score) {
        NotificationPanel panel = new NotificationPanel("+" + score);
        notificationGroup.getChildren().add(panel);
        panel.showScore(notificationGroup.getChildren());
    }

    /** Show a level-up notification (e.g., "LEVEL 2") */
    public void showLevelUpNotification(int level) {
        NotificationPanel panel = new NotificationPanel("LEVEL " + level);
        notificationGroup.getChildren().add(panel);
        panel.showLevel(notificationGroup.getChildren());
    }
}
