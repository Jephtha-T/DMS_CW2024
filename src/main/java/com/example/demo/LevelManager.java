package com.example.demo;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.lang.reflect.Constructor;

public class LevelManager {
    private static final Logger LOGGER = Logger.getLogger(LevelManager.class.getName());
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

            // Use reflection to dynamically load the level class
            Class<?> clazz = Class.forName(levelClassName);
            Constructor<?> constructor = clazz.getConstructor(double.class, double.class);
            currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

            currentLevel.addObserver(nextLevel -> {
                if (nextLevel instanceof String string) {
                    loadLevel(string);
                }
            });
            // Initialize and set the scene
            Scene scene = currentLevel.initializeScene();
            stage.setScene(scene);

            // Start the level
            currentLevel.startGame();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load level: {0}", e.getMessage());
        }
    }
}

