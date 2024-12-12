package com.example.demo;

import com.example.demo.actors.UserPlane;
import com.example.demo.actors.EnemyPlane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the boundary conditions of UserPlane and EnemyPlane.
 */
public class TestPlaneBounds {

    /**
     * Initializes JavaFX before all tests.
     */
    @BeforeAll
    public static void initJavaFX() {
        javafx.application.Platform.startup(() -> {});
    }

    /**
     * Sets system properties for headless testing before all tests.
     */
    @BeforeAll
    public static void initHeadless() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

    /**
     * Tests the vertical bounds of the UserPlane.
     * Ensures that the UserPlane does not move out of the upper and lower bounds.
     */
    @Test
    void testUserPlaneVerticalBounds() {
        UserPlane plane = new UserPlane(3);

        for (int i = 0; i < 100; i++) {
            plane.moveUp();
            plane.updatePosition();
        }
        assertTrue(plane.getLayoutY() + plane.getTranslateY() >= 0, "UserPlane moved out of upper bounds!");

        for (int i = 0; i < 100; i++) {
            plane.moveDown();
            plane.updatePosition();
        }
        assertTrue(plane.getLayoutY() + plane.getTranslateY() <= 750, "UserPlane moved out of lower bounds!");
    }

    /**
     * Tests the horizontal bounds of the EnemyPlane.
     * Ensures that the EnemyPlane does not move out of the screen horizontally.
     */
    @Test
    void testEnemyPlaneHorizontalBounds() {
        EnemyPlane plane = new EnemyPlane(0, 300);

        for (int i = 0; i < 200; i++) {
            plane.updatePosition();
        }
        assertTrue(plane.getLayoutX() + plane.getTranslateX() <= 1300, "EnemyPlane moved out of screen horizontally!");
    }

}