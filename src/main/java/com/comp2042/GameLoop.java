package com.comp2042;

import com.comp2042.Inputs_Events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;

public class GameLoop {

    private final Timeline timeline;

    public GameLoop(int delayMs, Consumer<MoveEvent> tickHandler) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(delayMs),
                e -> tickHandler.accept(null)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() { timeline.playFromStart(); }
    public void stop() { timeline.stop(); }
    public void pause() { timeline.pause(); }
    public void resume() { timeline.play(); }
    public void setRate(double rate) { timeline.setRate(rate); }
}
