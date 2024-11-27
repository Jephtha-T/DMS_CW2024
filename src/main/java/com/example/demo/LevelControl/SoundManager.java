package com.example.demo.LevelControl;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Objects;

public class SoundManager {
    private final HashMap<String, AudioClip> soundEffects = new HashMap<>();
    private MediaPlayer backgroundMusic;

    public SoundManager() {
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        soundEffects.put("explosion", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.EXPLOSION_AUDIO)).toString()));
        soundEffects.put("gameOver", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.GAME_OVER_AUDIO)).toString()));
        soundEffects.put("gunshot", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.GUNSHOT_AUDIO)).toString()));
        soundEffects.put("levelStart", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.START_AUDIO)).toString()));
        soundEffects.put("win", new AudioClip(Objects.requireNonNull(getClass().getResource(Config.WIN_AUDIO)).toString()));
    }

    public void playSoundEffect(String name) {
        AudioClip clip = soundEffects.get(name);
        if (clip != null) {
            clip.play();
        }
    }

    public void playBackgroundMusic(String musicPath) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toString());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
        backgroundMusic.play();
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        }
    }

    public void resumeBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PAUSED) {
            backgroundMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
}
