package com.comp2042;

import javafx.scene.control.Button;

import java.util.function.BiConsumer;

public class PauseManager {

    private boolean paused = false;
    private final BiConsumer<Button, Boolean> onToggle;

    public PauseManager(BiConsumer<Button, Boolean> onToggle) {
        this.onToggle = onToggle;
    }

    public boolean isPaused() { return paused; }

    public void togglePause(Button btn) {
        paused = !paused;
        if (onToggle != null) onToggle.accept(btn, paused);
    }

    public void setPaused(boolean value) { paused = value; }
}
