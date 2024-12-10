package com.example.demo;

import com.example.demo.actors.UserPlane;
import com.example.demo.actors.EnemyPlane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlaneBounds {

    @BeforeAll
    public static void initJavaFX() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeAll
    public static void initHeadless() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

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

    @Test
    void testEnemyPlaneHorizontalBounds() {
        EnemyPlane plane = new EnemyPlane(2000, 300);

        for (int i = 0; i < 200; i++) {
            plane.updatePosition();
        }
        assertTrue(plane.getLayoutX() + plane.getTranslateX() <= 1300, "EnemyPlane moved out of screen horizontally!");
    }
}
