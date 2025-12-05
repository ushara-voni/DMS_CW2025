package com.comp2042.logic.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Represents the player's score in Tetris.
 * Provides property binding support and methods to update or reset the score.
 */
public class Score {

    /** The current score property, supports JavaFX binding. */
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Returns the score property for binding with UI elements.
     *
     * @return IntegerProperty representing the current score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score.set(0);
    }

    /**
     * Adds a value to the current score.
     *
     * @param i the amount to add
     */
    public void add(int i){ score.setValue(score.getValue() + i); }

    /**
     * Gets the current score as an integer.
     *
     * @return current score
     */
    public int getScore() { return score.get(); }

}
