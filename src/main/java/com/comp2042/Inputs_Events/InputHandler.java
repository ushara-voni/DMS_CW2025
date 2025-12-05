package com.comp2042.Inputs_Events;

import com.comp2042.logic.core.ViewData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.function.Consumer;

/**
 * Handles keyboard input for the Tetris game and translates key presses
 * into movement or action events. The {@link InputHandler} communicates
 * with an {@link InputEventListener} to update game logic, and uses
 * callback functions to trigger UI refresh operations in the GUI layer.
 * This class does not manipulate the game state directly. Instead,
 * it acts as a bridge between JavaFX input events and the underlying
 * game logic.
 */
public class InputHandler {

    /** The game logic listener that receives movement and action events. */
    private final InputEventListener listener;

    /** Callback to update the UI with refreshed brick data. */
    private final Consumer<ViewData> refreshCallback;

    /** Callback for handling a downward (soft drop) event. */
    private final Consumer<MoveEvent> moveDownCallback;

    /** Callback for handling a hard drop event. */
    private final Consumer<MoveEvent> hardDropCallback;

    /** Callback for handling the "hold piece" action. */
    private final Consumer<MoveEvent> holdCallback;

    /**
     * Constructs a new InputHandler.
     *
     * @param listener           the logic listener for movement events
     * @param refreshCallback    called when a brick needs to be visually updated
     * @param moveDownCallback   called when a DOWN/S key is pressed
     * @param hardDropCallback   called when SPACE is pressed
     * @param holdCallback       called when C or SHIFT is pressed
     */
    public InputHandler(InputEventListener listener,
                        Consumer<ViewData> refreshCallback,
                        Consumer<MoveEvent> moveDownCallback,
                        Consumer<MoveEvent> hardDropCallback,
                        Consumer<MoveEvent> holdCallback) {
        this.listener = listener;
        this.refreshCallback = refreshCallback;
        this.moveDownCallback = moveDownCallback;
        this.hardDropCallback = hardDropCallback;
        this.holdCallback = holdCallback;

    }

    /**
     * Returns the JavaFX key handler responsible for processing all
     * in-game keyboard events. Keys are mapped as follows:
     *
     * <ul>
     *   <li>LEFT / A — move left</li>
     *   <li>RIGHT / D — move right</li>
     *   <li>UP / W — rotate</li>
     *   <li>DOWN / S — soft drop</li>
     *   <li>SPACE — hard drop</li>
     *   <li>C or SHIFT — hold piece</li>
     *   <li>N — start a new game</li>
     * </ul>
     *
     * @return the {@link EventHandler} for key events
     */
    public EventHandler<KeyEvent> getKeyHandler() {
        return keyEvent -> {
            if (listener == null) return;

            KeyCode code = keyEvent.getCode();

            switch (code) {
                case LEFT, A -> {
                    ViewData brick = listener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
                    refreshCallback.accept(brick);
                    keyEvent.consume();
                }
                case RIGHT, D -> {
                    ViewData brick = listener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
                    refreshCallback.accept(brick);
                    keyEvent.consume();
                }
                case UP, W -> {
                    ViewData brick = listener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
                    refreshCallback.accept(brick);
                    keyEvent.consume();
                }
                case DOWN, S -> {
                    MoveEvent downEvent = new MoveEvent(EventType.DOWN, EventSource.USER);
                    moveDownCallback.accept(downEvent);
                    keyEvent.consume();
                }
                case SPACE -> {
                    MoveEvent dropEvent = new MoveEvent(EventType.HARD_DROP, EventSource.USER);
                    hardDropCallback.accept(dropEvent);
                    keyEvent.consume();
                }
                case C, SHIFT -> {
                    MoveEvent holdEvent = new MoveEvent(EventType.HOLD, EventSource.USER);
                    holdCallback.accept(holdEvent);   // <-- note: use Consumer<MoveEvent>
                    keyEvent.consume();
                }

                case N -> listener.createNewGame();
            }
        };
    }
}
