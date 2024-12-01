package com.example.demo.controller;

import com.example.demo.levels.LevelManager;
import com.example.demo.levels.LevelParent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import java.util.logging.Logger;

/**
 * Controller class for the pause menu.
 * Handles user interactions with the pause menu, such as resuming the game, retrying the level,
 * returning to the main menu, and exiting the game.
 */
public class PauseMenuController {

    private static final Logger LOGGER = Logger.getLogger(PauseMenuController.class.getName()); // Logger instance

    private Runnable resumeCallback; // Callback to resume the game
    private LevelParent levelParent;

    @FXML
    private Button exitButton;

    /**
     * Sets the level parent.
     *
     * @param levelParent the parent level of the current game
     */
    public void setLevelParent(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Sets the resume callback.
     *
     * @param resumeCallback the callback to be executed to resume the game
     */
    public void setResumeCallback(Runnable resumeCallback) {
        this.resumeCallback = resumeCallback;
    }

    /**
     * Initializes the pause menu controller.
     * Sets a margin of 20px from the bottom and right for the exit button.
     */
    @FXML
    public void initialize() {
        // Set a margin of 20px from the bottom and right
        StackPane.setMargin(exitButton, new Insets(0, 30, 20, 0));
    }

    /**
     * Resumes the game.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void resumeGame(ActionEvent event) {
        runCallback("Resume callback is null. Unable to resume the game.");
    }

    /**
     * Retries the current level.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void retryLevel(ActionEvent event) {
        LOGGER.info("Retrying level...");
        if (levelParent != null) {
            runCallback("Resume callback is null. Unable to retry the game.");
            levelParent.stopGame();
            LevelManager.getInstance().loadLevel(levelParent.getClass().getName());
        } else {
            LOGGER.severe("Error: levelParent is null. Retry level failed.");
        }
    }

    /**
     * Runs the resume callback if it is not null, otherwise logs a warning message.
     *
     * @param warningMessage the warning message to log if the resume callback is null
     */
    private void runCallback(String warningMessage) {
        if (resumeCallback != null) {
            resumeCallback.run();
        } else {
            LOGGER.warning(warningMessage);
        }
    }

    /**
     * Returns to the main menu.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void goToMainMenu(ActionEvent event) {
        LOGGER.info("Returning to main menu...");
        LevelManager.getInstance().loadMainMenu();
    }

    /**
     * Exits the game.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void exitGame(ActionEvent event) {
        LOGGER.info("Exiting game...");
        System.exit(0);
    }
}