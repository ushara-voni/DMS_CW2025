package com.comp2042.Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {

    private static MediaPlayer bgmPlayer;

    // BACKGROUND MUSIC
    public static void playBGM(String fileName) {
        // Stop current track if any
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.dispose();
            bgmPlayer = null;
        }

        String path = MusicManager.class.getResource("/audio/" + fileName).toExternalForm();
        Media media = new Media(path);
        bgmPlayer = new MediaPlayer(media);

        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0.35);

        bgmPlayer.play();
    }

    public static void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.dispose();
            bgmPlayer = null;
        }
    }

    public static void setVolume(double vol) {
        if (bgmPlayer != null) {
            bgmPlayer.setVolume(vol);
        }
    }

    //  SOUND EFFECTS
    public static void playSFX(String fileName) {
        String path = MusicManager.class.getResource("/audio/" + fileName).toExternalForm();
        Media media = new Media(path);
        MediaPlayer sfx = new MediaPlayer(media);
        sfx.play();
        sfx.setOnEndOfMedia(sfx::dispose);
    }
}
