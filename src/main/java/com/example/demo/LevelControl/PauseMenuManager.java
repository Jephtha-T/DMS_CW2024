package com.example.demo.LevelControl;

import com.example.demo.Config;
import com.example.demo.controller.PauseMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;

public class PauseMenuManager {

    private final Group root;
    private LevelParent levelParent;
    private Parent pauseMenu;
    private int ScreenWidth = Config.SCREEN_WIDTH;
    private int ScreenHeight =Config.SCREEN_WIDTH;

    public PauseMenuManager(Group root, LevelParent levelParent) {
        this.root = root;
        this.levelParent = levelParent;
        initializePauseMenu();
    }

    private void initializePauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.PAUSE_MENU_FXML));
            pauseMenu = loader.load();

            // Get the controller and pass the required dependencies
            PauseMenuController controller = loader.getController();
            controller.setResumeCallback(levelParent::togglePause); // Set resume callback
            controller.setLevelParent(levelParent); // Pass the LevelParent reference

            pauseMenu.setVisible(false); // Initially hidden
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLevelParent(LevelParent levelParent) {
        this.levelParent = levelParent;
    }


    public void showPauseMenu() {
        if (!root.getChildren().contains(pauseMenu)) {
            root.getChildren().add(pauseMenu);
        }
        pauseMenu.setVisible(true);

        // Manually position the pause menu in the center of the screen
        double centerX = (ScreenWidth - pauseMenu.prefWidth(-1)) / 2;
        double centerY = (ScreenHeight - pauseMenu.prefHeight(-1)) / 4;

        pauseMenu.setLayoutX(centerX);
        pauseMenu.setLayoutY(centerY);
    }



    public void hidePauseMenu() {
        pauseMenu.setVisible(false);
    }



}
