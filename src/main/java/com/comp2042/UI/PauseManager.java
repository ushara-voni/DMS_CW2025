package com.comp2042.UI;

import javafx.scene.control.Button;

import java.util.function.BiConsumer;

/**
 * Manages the pause state of the game.
 * Allows toggling pause and notifies a callback when the state changes.
 */
public class PauseManager {

    /** Current pause state. */
    private boolean paused = false;
    /** Callback invoked when pause state is toggled. Parameters: Button, new paused state. */
    private final BiConsumer<Button, Boolean> onToggle;

    /**
     * Constructs a PauseManager with a specified toggle callback.
     *
     * @param onToggle callback to execute when pause is toggled; may be null
     */
    public PauseManager(BiConsumer<Button, Boolean> onToggle) {
        this.onToggle = onToggle;
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() { return paused; }

    /**
     * Toggles the pause state and invokes the callback if provided.
     *
     * @param btn the button associated with the pause toggle (for UI update)
     */
    public void togglePause(Button btn) {
        paused = !paused;
        if (onToggle != null) onToggle.accept(btn, paused);
    }


    /**
     * Explicitly sets the pause state.
     *
     * @param value true to pause, false to unpause
     */
    public void setPaused(boolean value) { paused = value; }
}
