package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.LevelControl.LevelManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuController {

    public static Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
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
