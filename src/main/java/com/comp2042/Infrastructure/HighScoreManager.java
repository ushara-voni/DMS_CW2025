package com.comp2042.Infrastructure;

import java.io.*;
/**
 * Utility class to manage reading and writing the high score to a file.
 * Stores the high score persistently in "highscore.dat".
 */
public class HighScoreManager {

    /** File name used to store the high score. */
    private static final String FILE_NAME = "highscore.dat";

    /**
     * Loads the stored high score from the file.
     * If the file does not exist or cannot be read, this method safely
     * returns {@code 0} as the default score.
     *
     * @return the stored high score, or {@code 0} if unavailable
     */
    public static int loadHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Saves a new high score to the file only if it exceeds the previous high score.
     *
     * @param newScore the new score to potentially save
     */
    public static void saveHighScore(int newScore) {
        int prevHigh = loadHighScore();
        if (newScore > prevHigh) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                pw.println(newScore);
            } catch (Exception ignored) {}
        }
    }
}
