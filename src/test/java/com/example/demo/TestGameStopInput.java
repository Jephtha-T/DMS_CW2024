package com.example.demo;

import com.example.demo.levels.PauseMenuManager;
import javafx.scene.Group;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestGameStopInput {

    @Test
    void testPauseMenuStopsInput() {
        Group root = new Group();
        PauseMenuManager manager = new PauseMenuManager(root, null);

        manager.showPauseMenu();
        assertTrue(root.getChildren().contains(manager), "Pause menu should be visible!");

    }
}
