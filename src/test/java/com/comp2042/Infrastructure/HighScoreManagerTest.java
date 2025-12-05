package com.comp2042.Infrastructure;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {

    private static final String FILE_NAME = "highscore.dat";
    private File file;

    @BeforeEach
    void setUp() {
        file = new File(FILE_NAME);
        if (file.exists()) file.delete();
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) file.delete();
    }

    @Test
    void testLoadHighScoreWhenFileNotExists() {
        assertEquals(0, HighScoreManager.loadHighScore());
    }

    @Test
    void testSaveAndLoadHighScore() {
        HighScoreManager.saveHighScore(50);
        assertEquals(50, HighScoreManager.loadHighScore());

        // Saving lower score should not overwrite
        HighScoreManager.saveHighScore(30);
        assertEquals(50, HighScoreManager.loadHighScore());

        // Saving higher score should overwrite
        HighScoreManager.saveHighScore(100);
        assertEquals(100, HighScoreManager.loadHighScore());
    }
}
