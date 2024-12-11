package com.example.demo.levels;

import com.example.demo.Config;

/**
 * Class representing an endless level in the game.
 * Extends the BaseLevel class and provides specific implementations for spawning enemies and items.
 */
public class LevelEndless extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;

    /**
     * Constructor for LevelEndless.
     * Initializes the endless level with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelEndless(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    @Override
    protected boolean shouldShowHelpImage() {
        return true; // Show HelpImage for LevelOne
    }


    /**
     * Spawns enemy units specific to the endless level.
     * This includes enemy planes, bomber planes, and charge planes.
     */
    @Override
    protected void spawnEnemyUnits() {
        double chance = new java.security.SecureRandom().nextDouble();

        if (chance < 0.4) { // 40% chance to spawn an enemy plane
            spawnEnemyPlane();
        } else if (chance < 0.7) { // 30% chance to spawn a bomber plane
            spawnBomberPlane();
        } else { // 30% chance to spawn a charge plane
            spawnChargePlane();
        }
    }

    /**
     * Spawns items specific to the endless level.
     * This includes shield items, multi-shot items, and health items.
     */
    @Override
    protected void spawnItems() {
        double chance = new java.security.SecureRandom().nextDouble();

        if (chance < 0.5) { // 50% chance for a shield item
            spawnShieldItem();
        } else if (chance < 0.8) { // 30% chance for a multi-shot item
            spawnMultiShotItem();
        } else { // 20% chance for a health item
            spawnHealthItem();
        }
    }

    /**
     * Gets the next level.
     * For the endless level, there is no next level.
     *
     * @return null as there is no next level
     */
    @Override
    protected String getNextLevel() {
        return null; // No next level
    }

    /**
     * Gets the number of kills required to advance to the next level.
     * For the endless level, there is no kill target.
     *
     * @return 0 as there is no kill target
     */
    @Override
    protected int getKillsToAdvance() {
        return 0; // No kill target
    }
}