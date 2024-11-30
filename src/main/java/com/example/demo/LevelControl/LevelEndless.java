package com.example.demo.LevelControl;

import com.example.demo.Actors.*;
import com.example.demo.Config;

public class LevelEndless extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
    private static final int TOTAL_ENEMIES = Config.TOTAL_ENEMIES;
    private static final int TOTAL_ITEMS = Config.TOTAL_ITEMS;
    private static final double ENEMY_SPAWN_PROBABILITY = Config.ENEMY_SPAWN_PROBABILITY;
    private static final double ITEM_SPAWN_PROBABILITY = Config.ITEM_SPAWN_PROBABILITY;
    private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;


    public LevelEndless(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double randomValue = Math.random(); // Random value between 0 and 1

                ActiveActorDestructible newEnemy;
                if (randomValue < 0.6) { // 60% chance for normal EnemyPlane
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                } else if(randomValue < 0.8){ // 20% chance for BomberPlane
                    newEnemy = BomberPlane.createInRightHalf(getScreenWidth(), getScreenHeight());
                } else{ // 20% chance for ChargerPlane
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    newEnemy = new ChargePlane(getScreenWidth(), newEnemyInitialYPosition);
                }
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected void spawnItems() {
        int currentNumberOfItems = getCurrentNumberOfItems();
        for (int i = 0; i < TOTAL_ITEMS - currentNumberOfItems; i++) {
            if (Math.random() < ITEM_SPAWN_PROBABILITY) {
                double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();

                // Randomly select an item type to spawn
                double itemTypeProbability = Math.random();
                if (itemTypeProbability < 1.0 / 3.0) {
                    // Spawn HealthItem
                    HealthItem newHeartItem = new HealthItem(getScreenWidth(), newItemInitialYPosition);
                    addItem(newHeartItem);
                } else if (itemTypeProbability < 2.0 / 3.0) {
                    // Spawn MultiShotItem
                    MultiShotItem newMultiShotItem = new MultiShotItem(getScreenWidth(), newItemInitialYPosition);
                    addItem(newMultiShotItem);
                } else {
                    // Spawn ShieldItem
                    ShieldItem newShieldItem = new ShieldItem(getScreenWidth(), newItemInitialYPosition);
                    addItem(newShieldItem);
                }
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getKillsToAdvance());
    }

    @Override
    protected int getKillsToAdvance() {
        return 0;
    }


}
