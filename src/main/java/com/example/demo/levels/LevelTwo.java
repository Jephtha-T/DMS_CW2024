package com.example.demo.levels;

import com.example.demo.Config;

/**
 * Class representing the second level in the game.
 * Extends the BaseLevel class and provides specific implementations for spawning enemies and items.
 */
public class LevelTwo extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
    private static final String NEXT_LEVEL = Config.LEVEL_THREE_CLASS;
    private static final int KILLS_TO_ADVANCE = Config.KILLS_TO_ADVANCE_LEVELTWO;

    /**
     * Constructor for LevelTwo.
     * Initializes the second level with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    /**
     * Spawns enemy units specific to the second level.
     * This includes enemy planes and bomber planes.
     */
    @Override
    protected void spawnEnemyUnits() {
        double chance = new java.security.SecureRandom().nextDouble();

        if (chance < 0.6) { // 60% chance to spawn an enemy plane
            spawnEnemyPlane();
        } else { // 40% chance to spawn a bomber plane
            spawnBomberPlane();
        }
    }

    /**
     * Spawns items specific to the second level.
     * This includes shield items and multi-shot items.
     */
    @Override
    protected void spawnItems() {
        double chance = new java.security.SecureRandom().nextDouble();

        if (chance < 0.5) { // 50% chance for a shield item
            spawnShieldItem();
        } else { // 50% chance for a multi-shot item
            spawnMultiShotItem();
        }
    }

    /**
     * Gets the next level.
     * For the second level, this is the third level.
     *
     * @return the class name of the next level
     */
    @Override
    protected String getNextLevel() {
        return NEXT_LEVEL;
    }

    /**
     * Gets the number of kills required to advance to the next level.
     * For the second level, this is a predefined number.
     *
     * @return the number of kills required to advance
     */
    @Override
    protected int getKillsToAdvance() {
        return KILLS_TO_ADVANCE;
    }
}