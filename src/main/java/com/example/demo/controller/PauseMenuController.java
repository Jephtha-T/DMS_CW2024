package com.example.demo.controller;

import com.example.demo.levels.LevelManager;
import com.example.demo.levels.LevelParent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import java.util.logging.Logger;

public class PauseMenuController {

    private static final Logger LOGGER = Logger.getLogger(PauseMenuController.class.getName()); // Logger instance

    private Runnable resumeCallback; // Callback to resume the game
    private LevelParent levelParent;
    @FXML
    private Button exitButton;

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
        } else {
            LOGGER.warning("Resume callback is null. Unable to resume the game.");
        }
    }

    @FXML
    private void retryLevel(ActionEvent event) {
        LOGGER.info("Retrying level...");
        if (resumeCallback != null) {
            resumeCallback.run(); // Ensure the game is resumed before retrying
        }
        if (levelParent != null) {
            levelParent.stopGame();
            LevelManager.getInstance().loadLevel(levelParent.getClass().getName()); // Reload the current level
        } else {
            LOGGER.severe("Error: levelParent is null. Retry level failed.");
        }
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        LOGGER.info("Returning to main menu...");
        LevelManager.getInstance().loadMainMenu();
    }

    @FXML
    private void exitGame(ActionEvent event) {
        LOGGER.info("Exiting game...");
        System.exit(0);
    }
}
