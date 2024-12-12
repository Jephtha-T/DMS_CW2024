package com.example.demo;

import com.example.demo.actors.ShieldItem;
import com.example.demo.actors.UserPlane;
import com.example.demo.actors.Boss;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the behavior of shielded units in the game.
 */
public class TestShieldedUnits {

    /**
     * Initializes JavaFX before all tests.
     */
    @BeforeAll
    public static void initJavaFX() {
        Platform.startup(() -> {});
    }

    /**
     * Tests that the shield on the UserPlane prevents damage.
     * Ensures that the health of the UserPlane remains the same after taking damage when the shield is active.
     */
    @Test
    void testShieldPreventsDamage() {
        UserPlane plane = new UserPlane(3);
        ShieldItem shield = new ShieldItem(0, 0);
        shield.triggerEffect(plane);

        int healthBefore = plane.getHealth();
        plane.takeDamage();
        assertEquals(healthBefore, plane.getHealth(), "Shield should prevent damage!");
    }

    /**
     * Tests that the shield on the Boss prevents damage.
     * Ensures that the health of the Boss remains the same after taking damage when the shield is active.
     */
    @Test
    void testBossShieldPreventsDamage() {
        Boss boss = new Boss();

        // Force shield activation through updateShield
        while (!boss.getShieldImage().isVisible()) { // Wait until shield is activated
            boss.updateActor();
        }

        int healthBefore = boss.getBossHealth();
        boss.takeDamage();
        assertEquals(healthBefore, boss.getBossHealth(), "Boss shield should prevent damage!");
    }

    /**
     * Tests that the Boss takes damage when the shield is not active.
     * Ensures that the health of the Boss decreases after taking damage when the shield is not active.
     */
    @Test
    void testBossTakesDamageWithoutShield() {
        Boss boss = new Boss();

        int healthBefore = boss.getBossHealth();
        boss.takeDamage();
        assertTrue(boss.getBossHealth() < healthBefore, "Boss should take damage when shield is not active!");
    }
}