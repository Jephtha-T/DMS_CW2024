package com.example.demo.levels;

import com.example.demo.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelManager {
    private static LevelManager instance; // Singleton instance
    private final Stage stage;
    private LevelParent currentLevel;

    // Private constructor to prevent direct instantiation
    public LevelManager(Stage stage) {
        this.stage = stage;
    }

    public static void initialize(Stage stage) {
        if (instance == null) {
            instance = new LevelManager(stage);
        }
    }

    public static LevelManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LevelManager has not been initialized.");
        }
        return instance;
    }

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



    public void loadMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/MainMenu.fxml"));
            Parent root = loader.load();


            MainMenuController.initialize(stage);

            Scene mainMenuScene = new Scene(root);
            stage.setScene(mainMenuScene);
        } catch (Exception e) {
            Logger.getLogger(LevelManager.class.getName()).log(Level.SEVERE, "Failed to load main menu", e);
        }
    }
}
