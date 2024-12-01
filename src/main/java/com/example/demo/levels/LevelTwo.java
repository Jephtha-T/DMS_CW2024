package com.example.demo.levels;

import com.example.demo.Config;

public class LevelTwo extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
    private static final String NEXT_LEVEL = Config.LEVEL_THREE_CLASS;
    private static final int KILLS_TO_ADVANCE = Config.KILLS_TO_ADVANCE_LEVELTWO;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    @Override
    protected void spawnEnemyUnits() {
        spawnEnemyPlane();
        spawnBomberPlane();
    }

    @Override
    protected void spawnItems() {
        spawnShieldItem();
        spawnMultiShotItem();
    }

    @Override
    protected String getNextLevel() {
        return NEXT_LEVEL;
    }

    @Override
    protected int getKillsToAdvance() {
        return KILLS_TO_ADVANCE;
    }
}