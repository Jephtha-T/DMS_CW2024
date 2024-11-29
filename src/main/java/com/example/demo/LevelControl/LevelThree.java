package com.example.demo.LevelControl;


import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Actors.Boss;
import com.example.demo.Actors.EnemyPlane;
import com.example.demo.Actors.ShieldItem;
import com.example.demo.Config;

import static com.example.demo.Config.KILLS_TO_ADVANCE;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_TWO_BACKGROUND;
    private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;
    private final Boss boss;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
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
        }
        else if (boss.isDestroyed()) {
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
        //No Items spawned in boss fight
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getKillsToAdvance());
    }

    @Override
    protected int getKillsToAdvance() {
        return KILLS_TO_ADVANCE;
    }


}