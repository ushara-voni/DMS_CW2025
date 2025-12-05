package com.comp2042.Infrastructure.Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Utility class for managing background music (BGM) and sound effects (SFX)
 * within the game. This class provides static methods to play, stop, and
 * adjust the volume of audio files stored in the {@code /audio/} resource
 * directory.
 *Background music uses a persistent {@link MediaPlayer} instance, while
 * sound effects create temporary players that automatically dispose
 * themselves after playback.
 */

public class MusicManager {

    /**  media player used for looping background music. */
    private static MediaPlayer bgmPlayer;


    /**
     * Plays a background music track from the {@code /audio/} resource folder.
     * If another track is already playing, it will be stopped and replaced.
     *
     * @param fileName the name of the audio file (e.g. {@code "theme.mp3"})
     */
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

    /**
     * Stops and disposes of the currently playing background music, if any.
     */
    public static void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.dispose();
            bgmPlayer = null;
        }
    }

    /**
     * Adjusts the volume of the background music.
     *
     * @param vol volume value between 0.0 (mute) and 1.0 (maximum)
     */
    public static void setVolume(double vol) {
        if (bgmPlayer != null) {
            bgmPlayer.setVolume(vol);
        }
    }

    /**
     * Plays a one-shot sound effect from the {@code /audio/} directory.
     * Each SFX uses a temporary {@link MediaPlayer} instance, which disposes
     * itself automatically when playback completes.
     *
     * @param fileName the sound effect file to play
     */
    public static void playSFX(String fileName) {
        String path = MusicManager.class.getResource("/audio/" + fileName).toExternalForm();
        Media media = new Media(path);
        MediaPlayer sfx = new MediaPlayer(media);
        sfx.play();
        sfx.setOnEndOfMedia(sfx::dispose);
    }
}
