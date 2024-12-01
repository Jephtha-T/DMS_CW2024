package com.example.demo.levels;

import com.example.demo.actors.*;
import com.example.demo.Config;

/**
 * Abstract base class for game levels.
 * Provides common functionality for managing game levels, including spawning enemies and items,
 * checking game over conditions, and initializing friendly units.
 */
public abstract class BaseLevel extends LevelParent {

    private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;

    /**
     * Constructor for BaseLevel.
     *
     * @param backgroundImageName the name of the background image for the level
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    protected BaseLevel(String backgroundImageName, double screenHeight, double screenWidth) {
        super(backgroundImageName, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the game is over.
     * If the user is destroyed, the game is lost.
     * If the user has reached the kill target and a transition is in progress, starts the level transition.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget() && isTransitionInProgress()) {
            startLevelTransition();
            notifyObservers(getNextLevel());
        }
    }

    /**
     * Initializes friendly units in the level.
     * Adds the user to the root node.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns an enemy plane at a random Y position.
     */
    protected void spawnEnemyPlane() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        EnemyPlane newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
        addEnemyUnit(newEnemy);
    }

    /**
     * Spawns a bomber plane in the right half of the screen.
     */
    protected void spawnBomberPlane() {
        BomberPlane newEnemy = BomberPlane.createInRightHalf(getScreenWidth());
        addEnemyUnit(newEnemy);
    }

    /**
     * Spawns a charge plane at a random Y position.
     */
    protected void spawnChargePlane() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        ChargePlane newEnemy = new ChargePlane(getScreenWidth(), newEnemyInitialYPosition);
        addEnemyUnit(newEnemy);
    }

    /**
     * Spawns a shield item at a random Y position.
     */
    protected void spawnShieldItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        ShieldItem newShieldItem = new ShieldItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newShieldItem);
    }

    /**
     * Spawns a multi-shot item at a random Y position.
     */
    protected void spawnMultiShotItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        MultiShotItem newMultiShotItem = new MultiShotItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newMultiShotItem);
    }

    /**
     * Spawns a health item at a random Y position.
     */
    protected void spawnHealthItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        HealthItem newHeartItem = new HealthItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newHeartItem);
    }

    /**
     * Instantiates the level view.
     *
     * @return the instantiated LevelView
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getKillsToAdvance());
    }

    /**
     * Gets the next level.
     *
     * @return the name of the next level
     */
    protected abstract String getNextLevel();

    /**
     * Gets the number of kills required to advance to the next level.
     *
     * @return the number of kills required to advance
     */
    protected abstract int getKillsToAdvance();

    /**
     * Checks if the user has reached the kill target.
     *
     * @return true if the user has reached the kill target, false otherwise
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= getKillsToAdvance();
    }

    /**
     * Spawns enemies and items if the current number is less than the configured total.
     */
    protected void spawnEnemiesAndItems() {
        if(getCurrentNumberOfEnemies() < Config.TOTAL_ENEMIES) {
            spawnEnemyUnits();
        }
        if(getCurrentNumberOfItems() < Config.TOTAL_ITEMS) {
            spawnItems();
        }
    }
}