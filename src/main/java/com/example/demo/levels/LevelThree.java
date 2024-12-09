package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.Boss;

/**
 * Class representing the third level in the game.
 * Extends the BaseLevel class and provides specific implementations for spawning enemies and items.
 */
public class LevelThree extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_TWO_BACKGROUND;
    private static final Boss boss = new Boss(); // The boss enemy for this level

    /**
     * Constructor for LevelThree.
     * Initializes the third level with the specified screen dimensions and creates the boss enemy.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    /**
     * Initializes friendly units for the level.
     * Adds the user plane to the root group.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Gets the boss enemy for this level.
     *
     * @return the boss enemy
     */
    public static Boss getBoss() {
        return boss;
    }

    /**
     * Checks if the game is over.
     * The game is over if the user plane is destroyed or the boss is destroyed.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Spawns enemy units specific to the third level.
     * Adds the boss enemy and its shield image to the root group if no enemies are currently present.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            getRoot().getChildren().add(boss.getShieldImage());
        }
    }

    /**
     * Spawns items specific to the third level.
     * No items for Boss Fight
     */
    @Override
    protected void spawnItems() {
        // No items for Boss Fight
    }

    /**
     * Gets the next level.
     * For the third level, there is no next level.
     *
     * @return null as there is no next level
     */
    @Override
    protected String getNextLevel() {
        return null; // No next level
    }

    /**
     * Gets the number of kills required to advance to the next level.
     * For the third level, there is no kill target.
     *
     * @return 0 as there is no kill target
     */
    @Override
    protected int getKillsToAdvance() {
        return 0; // No kill target
    }
}