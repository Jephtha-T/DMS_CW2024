package com.example.demo.levels;

import com.example.demo.Config;

public class LevelEndless extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;

    public LevelEndless(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    @Override
    protected void spawnEnemyUnits() {
        spawnEnemyPlane();
        spawnBomberPlane();
        spawnChargePlane();
    }

    @Override
    protected void spawnItems() {
        spawnShieldItem();
        spawnMultiShotItem();
        spawnHealthItem();
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