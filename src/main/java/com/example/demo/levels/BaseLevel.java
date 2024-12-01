package com.example.demo.levels;

import com.example.demo.actors.*;
import com.example.demo.Config;

public abstract class BaseLevel extends LevelParent {

    private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;

    protected BaseLevel(String backgroundImageName, double screenHeight, double screenWidth) {
        super(backgroundImageName, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget() && isTransitionInProgress()) {
            startLevelTransition();
            notifyObservers(getNextLevel());
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    protected void spawnEnemyPlane() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        EnemyPlane newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
        addEnemyUnit(newEnemy);
    }

    protected void spawnBomberPlane() {
        BomberPlane newEnemy = BomberPlane.createInRightHalf(getScreenWidth());
        addEnemyUnit(newEnemy);
    }

    protected void spawnChargePlane() {
        double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        ChargePlane newEnemy = new ChargePlane(getScreenWidth(), newEnemyInitialYPosition);
        addEnemyUnit(newEnemy);
    }

    protected void spawnShieldItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        ShieldItem newShieldItem = new ShieldItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newShieldItem);
    }

    protected void spawnMultiShotItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        MultiShotItem newMultiShotItem = new MultiShotItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newMultiShotItem);
    }

    protected void spawnHealthItem() {
        double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
        HealthItem newHeartItem = new HealthItem(getScreenWidth(), newItemInitialYPosition);
        addItem(newHeartItem);
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getKillsToAdvance());
    }

    protected abstract String getNextLevel();

    protected abstract int getKillsToAdvance();

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= getKillsToAdvance();
    }

    protected void spawnEnemiesAndItems() {
        if(getCurrentNumberOfEnemies() < Config.TOTAL_ENEMIES) {
            spawnEnemyUnits();
        }
        if(getCurrentNumberOfItems() < Config.TOTAL_ITEMS) {
            spawnItems();
        }
    }
}