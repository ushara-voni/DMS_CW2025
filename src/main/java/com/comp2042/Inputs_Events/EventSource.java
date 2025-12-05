package com.comp2042.Inputs_Events;

/**
 * Represents the origin of an event within the application.
 * Events may be triggered directly by the user (e.g., keyboard input)
 * or by background logic running on a separate thread (e.g., game loop updates).
 */
public enum EventSource {
    /**
     * Event triggered by direct user interaction,
     * such as key presses or UI actions.
     */
    USER,

    /**
     * Event triggered by an internal game thread,
     * such as automated falling of tetrominoes or timed updates.
     */
    THREAD
}
