package com.comp2042.UI;

import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class PauseManagerTest {

    private PauseManager manager;

    @BeforeEach
    void setUp() {
        manager = new PauseManager(null); // no callback
    }

    @Test
    void testInitialPausedState() {
        assertFalse(manager.isPaused());
    }

    @Test
    void testTogglePause() {
        manager.togglePause(null);
        assertTrue(manager.isPaused());

        manager.togglePause(null);
        assertFalse(manager.isPaused());
    }

    @Test
    void testSetPaused() {
        manager.setPaused(true);
        assertTrue(manager.isPaused());

        manager.setPaused(false);
        assertFalse(manager.isPaused());
    }

    @Test
    void testCallbackInvocation() {
        AtomicBoolean called = new AtomicBoolean(false);
        manager = new PauseManager((btn, paused) -> called.set(paused));

        manager.togglePause(null);
        assertTrue(called.get());
    }
}
