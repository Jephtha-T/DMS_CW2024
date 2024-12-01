package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.controller.PauseMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Group;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the pause menu in the game.
 * Handles the initialization, display, and hiding of the pause menu.
 */
public class PauseMenuManager {

    private final Group root; // The root group for adding the pause menu
    private final LevelParent levelParent; // Reference to the parent level
    private Parent pauseMenu; // The pause menu UI element

    /**
     * Constructor for PauseMenuManager.
     * Initializes the pause menu manager with the specified root group and parent level.
     *
     * @param root the root group for adding the pause menu
     * @param levelParent the parent level
     */
    public PauseMenuManager(Group root, LevelParent levelParent) {
        this.root = root;
        this.levelParent = levelParent;
        initializePauseMenu();
    }

    /**
     * Initializes the pause menu.
     * Loads the pause menu FXML and sets up the controller.
     */
    private void initializePauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.PAUSE_MENU_FXML));
            pauseMenu = loader.load();

            // Get the controller and pass the required dependencies
            PauseMenuController controller = loader.getController();
            controller.setResumeCallback(levelParent::togglePause); // Set resume callback
            controller.setLevelParent(levelParent); // Pass the LevelParent reference

            assert pauseMenu != null;
            pauseMenu.setVisible(false); // Initially hidden
        } catch (Exception e) {
            Logger.getLogger(PauseMenuManager.class.getName()).log(Level.SEVERE, "Failed to load pause menu FXML", e);
        }
    }

    /**
     * Shows the pause menu.
     * Adds the pause menu to the root group if not already added and makes it visible.
     * Positions the pause menu in the center of the screen.
     */
    public void showPauseMenu() {
        if (!root.getChildren().contains(pauseMenu)) {
            root.getChildren().add(pauseMenu);
        }
        pauseMenu.setVisible(true);

        // Manually position the pause menu in the center of the screen
        int screenWidth = Config.SCREEN_WIDTH;
        double centerX = (screenWidth - pauseMenu.prefWidth(-1)) / 2;
        int screenHeight = Config.SCREEN_HEIGHT;
        double centerY = (screenHeight - pauseMenu.prefHeight(-1)) / 2;

        pauseMenu.setLayoutX(centerX);
        pauseMenu.setLayoutY(centerY);
    }

    /**
     * Hides the pause menu.
     * Makes the pause menu invisible.
     */
    public void hidePauseMenu() {
        pauseMenu.setVisible(false);
    }
}