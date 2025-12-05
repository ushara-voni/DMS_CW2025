package com.comp2042.UI;

import javafx.scene.layout.GridPane;
import com.comp2042.logic.core.ViewData;

/**
 * Utility class to position a falling or ghost brick panel on the game UI.
 * Calculates the pixel position based on the brick's coordinates and panel layout.
 */
public class BrickPositioner {
    /**
     * Updates the position of a brick's GridPane relative to the game panel.
     *
     * @param brickPanel the GridPane representing the brick to position
     * @param gamePanel the main game board panel used for coordinate reference
     * @param brick the current brick's ViewData containing x/y positions
     * @param size the pixel size of each brick block
     * @param yOffset additional vertical offset to apply
     */
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
