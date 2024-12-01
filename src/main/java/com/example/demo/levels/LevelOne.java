package com.example.demo.levels;

import com.example.demo.Config;

/**
 * Class representing the first level in the game.
 * Extends the BaseLevel class and provides specific implementations for spawning enemies and items.
 */
public class LevelOne extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
    private static final String NEXT_LEVEL = Config.LEVEL_TWO_CLASS;
    private static final int KILLS_TO_ADVANCE = Config.KILLS_TO_ADVANCE_LEVELONE;

    /**
     * Constructor for LevelOne.
     * Initializes the first level with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelOne(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    /**
     * Spawns enemy units specific to the first level.
     * This includes enemy planes.
     */
    @Override
    protected void spawnEnemyUnits() {
        spawnEnemyPlane();
    }

    /**
     * Spawns items specific to the first level.
     * This includes shield items.
     */
    @Override
    protected void spawnItems() {
        spawnShieldItem();
    }

    /**
     * Gets the next level.
     * For the first level, this is the second level.
     *
     * @return the class name of the next level
     */
    @Override
    protected String getNextLevel() {
        return NEXT_LEVEL;
    }

    /**
     * Gets the number of kills required to advance to the next level.
     * For the first level, this is a predefined number.
     *
     * @return the number of kills required to advance
     */
    @Override
    protected int getKillsToAdvance() {
        return KILLS_TO_ADVANCE;
    }
}