package com.comp2042.UI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void reset() {
        score.set(0);
    }

    public void add(int i){ score.setValue(score.getValue() + i); }

    public int getScore() { return score.get(); }

}
