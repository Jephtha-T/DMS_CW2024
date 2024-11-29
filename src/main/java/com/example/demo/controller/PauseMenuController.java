package com.example.demo.controller;

import com.example.demo.LevelControl.LevelManager;
import com.example.demo.LevelControl.LevelParent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class PauseMenuController {

    private Runnable resumeCallback; // Callback to resume the game
    private LevelParent levelParent;
    private LevelManager levelManager;
    @FXML
    private Button exitButton;

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void setLevelParent(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Sets the callback function to resume the game.
     *
     * @param resumeCallback A Runnable to be called when resuming.
     */
    public void setResumeCallback(Runnable resumeCallback) {
        this.resumeCallback = resumeCallback;
    }

    @FXML
    public void initialize() {
        // Set a margin of 20px from the bottom and right
        StackPane.setMargin(exitButton, new Insets(0, 30, 20, 0));
    }

    @FXML
    private void resumeGame(ActionEvent event) {
        if (resumeCallback != null) {
            resumeCallback.run(); // Call the resume logic
        }
    }

    @FXML
    private void retryLevel(ActionEvent event) {
        System.out.println("Retrying level...");
        if (resumeCallback != null) {
            resumeCallback.run(); // Ensure the game is resumed before retrying
        }
        if (levelParent != null) {
            levelParent.stopGame();
            LevelManager.getInstance().loadLevel(levelParent.getClass().getName()); // Reload the current level
        } else {
            System.err.println("Error: levelParent is null. Retry level failed.");
        }
    }


    @FXML
    private void goToMainMenu(ActionEvent event) {
        System.out.println("Returning to main menu...");
        LevelManager.getInstance().loadMainMenu();
    }



    @FXML
    private void exitGame(ActionEvent event) {
        System.out.println("Exiting game...");
        System.exit(0);
    }
}
