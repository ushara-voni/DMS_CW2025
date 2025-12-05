package com.comp2042.UI;

import com.comp2042.Inputs_Events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Represents the main game loop for Tetris, handling timed updates for piece movement.
 * Uses a JavaFX Timeline to trigger tick events at a fixed interval.
 */
public class GameLoop {


    /** Timeline controlling the periodic execution of game ticks. */
    private final Timeline timeline;

    /**
     * Constructs a game loop with a specified delay and tick handler.
     *
     * @param delayMs time in milliseconds between each game tick
     * @param tickHandler consumer called on every tick with the current MoveEvent
     */
    public GameLoop(int delayMs, Consumer<MoveEvent> tickHandler) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(delayMs),
                e -> tickHandler.accept(null)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the game loop from the beginning.
     */
    public void start() { timeline.playFromStart(); }
    /**
     * Stops the game loop completely.
     */
    public void stop() { timeline.stop(); }

    /**
     * Pauses the game loop temporarily.
     */
    public void pause() { timeline.pause(); }
    /**
     * Resumes the game loop from a paused state.
     */
    public void resume() { timeline.play(); }

    /**
     * Sets the speed multiplier for the game loop.
     *
     * @param rate the playback rate (1.0 = normal speed)
     */
    public void setRate(double rate) { timeline.setRate(rate); }
}
