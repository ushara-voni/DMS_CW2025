package com.comp2042.UI;

import javafx.scene.layout.GridPane;

public class BrickPositioner {

    public static void update(GridPane brickPanel, GridPane gamePanel,
                              ViewData brick, int size, int yOffset) {
        if (brick == null || brickPanel == null || gamePanel == null) return;

        double x = gamePanel.getLayoutX() + brick.getxPosition() * size;
        double y = gamePanel.getLayoutY() + brick.getyPosition() * size + yOffset;

        brickPanel.setLayoutX(x);
        brickPanel.setLayoutY(y);
        brickPanel.toFront();
    }
}
