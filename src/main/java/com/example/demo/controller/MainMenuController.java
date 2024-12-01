package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.levels.LevelManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuController {

    private static Stage stage; // Make private and static to limit access

    // Static initializer for setting the stage
    public static void initialize(Stage primaryStage) {
        if (stage == null) {
            stage = primaryStage; // Set only once
        } else {
            throw new IllegalStateException("Stage has already been initialized.");
        }
    }

    // Provide an accessor if needed
    public static Stage getStage() {
        return stage;
    }

    @FXML
    public void playLevelOne() {
        if (stage == null) {
            throw new IllegalStateException("Stage has not been set for MainMenuController.");
        }

        LevelManager levelManager = new LevelManager(stage);
        levelManager.loadLevel(Config.LEVEL_ONE_CLASS);
    }

    @FXML
    public void playEndless() {
        if (stage == null) {
            throw new IllegalStateException("Stage has not been set for MainMenuController.");
        }

        LevelManager levelManager = new LevelManager(stage);
        levelManager.loadLevel(Config.LEVEL_ENDLESS_CLASS);
    }

    @FXML
    public void exitGame() {
        Platform.exit();
    }
}
