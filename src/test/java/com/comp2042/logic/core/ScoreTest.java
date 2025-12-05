package com.comp2042.logic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void testInitialScore() {
        assertEquals(0, score.getScore());
    }

    @Test
    void testAddScore() {
        score.add(10);
        assertEquals(10, score.getScore());

        score.add(5);
        assertEquals(15, score.getScore());
    }

    @Test
    void testResetScore() {
        score.add(50);
        score.reset();
        assertEquals(0, score.getScore());
    }
}
