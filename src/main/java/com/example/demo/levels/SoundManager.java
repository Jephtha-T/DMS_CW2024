package com.example.demo.levels;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Objects;

/**
 * Manages the sound effects and background music for the game.
 * Provides methods to play, pause, resume, and stop sounds.
 */
public class SoundManager {
    private final HashMap<String, AudioClip> soundEffects = new HashMap<>();
    private MediaPlayer backgroundMusic;

    /**
     * Constructor for SoundManager.
     * Initializes the sound manager and loads sound effects.
     */
    public SoundManager() {
        loadSoundEffects();
    }

    /**
     * Loads sound effects from the specified audio files.
     * Adds the sound effects to the soundEffects map.
     */
    private void loadSoundEffects() {
        soundEffects.put("explosion", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.EXPLOSION_AUDIO)).toString()));
        soundEffects.put("gameOver", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.GAME_OVER_AUDIO)).toString()));
        soundEffects.put("gunshot", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.GUNSHOT_AUDIO)).toString()));
        soundEffects.put("levelStart", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.START_AUDIO)).toString()));
        soundEffects.put("win", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.WIN_AUDIO)).toString()));
    }

    /**
     * Plays the specified sound effect.
     *
     * @param name the name of the sound effect to play
     */
    public void playSoundEffect(String name) {
        AudioClip clip = soundEffects.get(name);
        if (clip != null) {
            clip.play();
        }
    }

    /**
     * Plays the background music from the specified file path.
     * Stops any currently playing background music before starting the new one.
     *
     * @param musicPath the file path of the background music to play
     */
    public void playBackgroundMusic(String musicPath) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toString());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
        backgroundMusic.play();
    }

    /**
     * Pauses the currently playing background music.
     */
    public void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        }
    }

    /**
     * Resumes the currently paused background music.
     */
    public void resumeBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PAUSED) {
            backgroundMusic.play();
        }
    }

    /**
     * Stops the currently playing background music and releases resources.
     */
    void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose(); // Properly release resources if supported
            backgroundMusic = null;   // Nullify to avoid dangling references
        }
    }
}