package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.Boss;

public class LevelThree extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_TWO_BACKGROUND;
    private final Boss boss;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
        boss = new Boss();
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            getRoot().getChildren().add(boss.getShieldImage());
        }
    }

    @Override
    protected void spawnItems() {
        spawnShieldItem();
    }

    @Override
    protected String getNextLevel() {
        return null; // No next level
    }

    @Override
    protected int getKillsToAdvance() {
        return 0; // No kill target
    }
}