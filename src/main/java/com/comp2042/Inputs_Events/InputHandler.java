package com.comp2042.Inputs_Events;

import com.comp2042.UI.ViewData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.function.Consumer;

public class InputHandler {

    private final InputEventListener listener;
    private final Consumer<ViewData> refreshCallback;
    private final Consumer<MoveEvent> moveDownCallback;
    private final Consumer<MoveEvent> hardDropCallback;

    public InputHandler(InputEventListener listener,
                        Consumer<ViewData> refreshCallback,
                        Consumer<MoveEvent> moveDownCallback,
                        Consumer<MoveEvent> hardDropCallback) {
        this.listener = listener;
        this.refreshCallback = refreshCallback;
        this.moveDownCallback = moveDownCallback;
        this.hardDropCallback = hardDropCallback;
    }

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
                case N -> listener.createNewGame();
            }
        };
    }
}
