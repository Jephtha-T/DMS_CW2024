package com.example.demo;

import com.example.demo.levels.LevelEndless;
import com.example.demo.levels.LevelManager;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the limits of enemies and items in the LevelEndless class.
 */
public class TestLevelLimits {

    /**
     * Initializes JavaFX and LevelManager with a mock stage before all tests.
     */
    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX and LevelManager with a mock stage
        Platform.startup(() -> {});
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            LevelManager.initialize(mockStage);
        });
    }

    /**
     * Tests the maximum limit of enemies in the LevelEndless class.
     * Ensures that the number of enemies does not exceed the specified limit.
     */
    @Test
    void testMaxEnemyLimit() {
        // Ensure JavaFX thread initializes properly
        Platform.runLater(() -> {
            LevelEndless level = new LevelEndless(1080, 1920);

            for (int i = 0; i < 15; i++) {
                level.updateScene();
            }

            assertTrue(level.getCurrentNumberOfEnemies() <= 10, "Enemy count exceeded level limit!");
        });
    }

    /**
     * Tests the maximum limit of items in the LevelEndless class.
     * Ensures that the number of items does not exceed the specified limit.
     */
    @Test
    void testMaxItemLimit() {
        // Ensure JavaFX thread initializes properly
        Platform.runLater(() -> {
            LevelEndless level = new LevelEndless(1080, 1920);

            for (int i = 0; i < 10; i++) {
                level.updateScene();
            }

            assertTrue(level.getCurrentNumberOfItems() <= 5, "Item count exceeded level limit!");
        });
    }
}