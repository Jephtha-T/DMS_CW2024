package com.example.demo.levels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the loading and transitioning between different levels in the game.
 * Implements the Singleton pattern to ensure only one instance is used.
 */
public class LevelManager {
    private static LevelManager instance; // Singleton instance
    private final Stage stage; // The primary stage for displaying levels
    private LevelParent currentLevel; // The currently active level

    /**
     * Private constructor to prevent direct instantiation.
     *
     * @param stage the primary stage for displaying levels
     */
    public LevelManager(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the LevelManager with the given stage.
     * Should be called once at the start of the application.
     *
     * @param stage the primary stage for displaying levels
     */
    public static void initialize(Stage stage) {
        if (instance == null) {
            instance = new LevelManager(stage);
        }
    }

    /**
     * Gets the singleton instance of LevelManager.
     *
     * @return the singleton instance of LevelManager
     * @throws IllegalStateException if the LevelManager has not been initialized
     */
    public static LevelManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LevelManager has not been initialized.");
        }
        return instance;
    }

    /**
     * Loads a level by its class name.
     * Stops the current level's game loop if a level is already loaded.
     * Uses reflection to dynamically load the level class.
     *
     * @param levelClassName the fully qualified class name of the level to load
     */
    public void loadLevel(String levelClassName) {
        try {
            if (currentLevel != null) {
                currentLevel.stopGame(); // Stop the current level's game loop
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

            // Initialize the scene
            Scene scene = currentLevel.initializeScene();

            // Set the scene on the stage without modifying its size
            stage.setScene(scene);

            // Ensure the stage retains its original size
            stage.setWidth(stage.getWidth());
            stage.setHeight(stage.getHeight());

            currentLevel.startGame();
        } catch (Exception e) {
            Logger.getLogger(LevelManager.class.getName()).log(Level.SEVERE, e, () -> "Failed to load level: " + levelClassName);
        }
    }

    /**
     * Loads the main menu scene.
     * Uses FXMLLoader to load the FXML file for the main menu.
     */
    public void loadMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/MainMenu.fxml"));
            Parent root = loader.load();

            Scene mainMenuScene = new Scene(root);
            stage.setScene(mainMenuScene);
        } catch (Exception e) {
            Logger.getLogger(LevelManager.class.getName()).log(Level.SEVERE, "Failed to load main menu", e);
        }
    }
}