package com.comp2042.UI;

import java.io.*;

public class HighScoreManager {

    private static final String FILE_NAME = "highscore.dat";

    /** Return stored high score or 0 if file missing. */
    public static int loadHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(br.readLine().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /** Save ONLY if new score is higher. */
    public static void saveHighScore(int newScore) {
        int prevHigh = loadHighScore();
        if (newScore > prevHigh) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                pw.println(newScore);
            } catch (Exception ignored) {}
        }
    }
}
