package com.example.demo.levels;

import com.example.demo.Config;

public class LevelOne extends BaseLevel {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
    private static final String NEXT_LEVEL = Config.LEVEL_TWO_CLASS;
    private static final int KILLS_TO_ADVANCE = Config.KILLS_TO_ADVANCE_LEVELONE;

    public LevelOne(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth);
    }

    @Override
    protected void spawnEnemyUnits() {
        spawnEnemyPlane();
    }

    @Override
    protected void spawnItems() {
        spawnShieldItem();
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