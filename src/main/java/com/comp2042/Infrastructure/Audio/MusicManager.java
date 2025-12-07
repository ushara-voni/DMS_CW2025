package com.comp2042.Infrastructure.Audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Singleton class responsible for managing all background music (BGM)
 * and sound effects (SFX) in the game.
 *
 * Ensures only one global instance of the audio controller exists, preventing
 * overlapping tracks and maintaining consistent audio across different screens.
 */
public class MusicManager {

    /** The single global instance of MusicManager. */
    private static final MusicManager instance = new MusicManager();

    /** Media player used for looping background music. */
    private MediaPlayer bgmPlayer;

    /** Private constructor to prevent external instantiation. */
    private MusicManager() {}

    /**
     * Provides access to the single MusicManager instance.
     *
     * @return the global MusicManager instance
     */
    public static MusicManager getInstance() {
        return instance;
    }

    /**
     * Plays a background music track from the /audio/ resource folder.
     * If another track is already playing, it will be stopped and replaced.
     *
     * @param fileName the name of the audio file (e.g. "theme.mp3")
     */
    public void playBGM(String fileName) {
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
    public void stopBGM() {
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
    public void setVolume(double vol) {
        if (bgmPlayer != null) {
            bgmPlayer.setVolume(vol);
        }
    }

    /**
     * Plays a one-shot sound effect from the /audio/ directory.
     * Each SFX uses a temporary MediaPlayer instance that automatically
     * disposes itself after playback.
     *
     * @param fileName the sound effect file to play
     */
    public void playSFX(String fileName) {
        String path = MusicManager.class.getResource("/audio/" + fileName).toExternalForm();
        Media media = new Media(path);
        MediaPlayer sfx = new MediaPlayer(media);

        sfx.play();
        sfx.setOnEndOfMedia(sfx::dispose);
    }
}
