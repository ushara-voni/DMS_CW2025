package com.comp2042.Inputs_Events;

/**
 * Represents a movement or action event in the Tetris game.
 * Each {@code MoveEvent} encapsulates the type of action being performed
 * (such as move left, rotate, or hard drop) and the source of the event
 * (user input or internal game thread).
 */
public final class MoveEvent {
    /** The type of event (LEFT, RIGHT, ROTATE, DOWN, HARD_DROP, HOLD). */
    private final EventType eventType;

    /** The source of the event (USER or THREAD). */
    private final EventSource eventSource;


    /**
     * Constructs a new MoveEvent with the specified type and source.
     *
     * @param eventType   the type of event
     * @param eventSource the source of the event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of this event.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the source of this event.
     *
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
