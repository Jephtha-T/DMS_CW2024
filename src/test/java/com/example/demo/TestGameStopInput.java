package com.example.demo;

import com.example.demo.levels.PauseMenuManager;
import javafx.scene.Group;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the behavior of the pause menu in stopping game input.
 */
class TestGameStopInput {

    /**
     * Tests that the pause menu stops input.
     * Ensures that the pause menu is visible when shown.
     */
    @Test
    void testPauseMenuStopsInput() {
        Group root = new Group();
        PauseMenuManager manager = new PauseMenuManager(root, null);

        manager.showPauseMenu();
        assertTrue(root.getChildren().contains(manager), "Pause menu should be visible!");
    }
}