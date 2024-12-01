package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.levels.LevelManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for the main menu.
 * Handles user interactions with the main menu, such as starting levels and exiting the game.
 */
public class MainMenuController {

    private static Stage stage; // Make private and static to limit access

    /**
     * Static initializer for setting the stage.
     * Ensures the stage is set only once.
     *
     * @param primaryStage the primary stage for the application
     * @throws IllegalStateException if the stage has already been initialized
     */
    public static void initialize(Stage primaryStage) {
        if (stage == null) {
            stage = primaryStage; // Set only once
        } else {
            throw new IllegalStateException("Stage has already been initialized.");
        }
    }

    /**
     * Provides access to the stage.
     *
     * @return the primary stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Starts the specified level.
     *
     * @param level the class name of the level to start
     */
    @FXML
    public void playLevel(String level) {
        try {
            if (stage == null) {
                throw new IllegalStateException("Stage has not been set.");
            }
            LevelManager levelManager = new LevelManager(stage);
            levelManager.loadLevel(level);
        } catch (Exception e) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, () -> "Failed to load level: " + e.getMessage());
            // Provide fallback or error UI here
        }
    }

    /**
     * Starts level one.
     */
    @FXML
    public void playLevelOne() {
        playLevel(Config.LEVEL_ONE_CLASS);
    }

    /**
     * Starts the endless level.
     */
    @FXML
    public void playEndless() {
        playLevel(Config.LEVEL_ENDLESS_CLASS);
    }

    /**
     * Exits the game.
     */
    @FXML
    public void exitGame() {
        Platform.exit();
    }
}