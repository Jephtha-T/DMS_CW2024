package com.example.demo;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

public class LevelManager {
    private final Stage stage;
    private LevelParent currentLevel;

    public LevelManager(Stage stage) {
        this.stage = stage;
    }

    public void loadLevel(String levelClassName) {
        try {
            if (currentLevel != null) {
                currentLevel.stopGame(); // Stop the previous level's timeline
            }
            System.out.println("Loading level: " + levelClassName);

            // Use reflection to dynamically load the level class
            Class<?> clazz = Class.forName(levelClassName);
            Constructor<?> constructor = clazz.getConstructor(double.class, double.class);
            currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

            currentLevel.addObserver(nextLevel -> {
                if (nextLevel instanceof String) {
                    loadLevel((String) nextLevel);
                }
            });
            // Initialize and set the scene
            Scene scene = currentLevel.initializeScene();
            stage.setScene(scene);

            // Start the level
            currentLevel.startGame();
            System.out.println("Level " + levelClassName + " started.");


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load level: " + e.getMessage());
        }
    }

    private void onLevelComplete(Object nextLevel) {
        if (nextLevel instanceof String) {
            loadLevel((String) nextLevel);
        }
    }
}

