package com.example.demo;

public final class Config {

    // General Game Configuration
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    public static final int MILLISECOND_DELAY = 50;
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 50.0;
    public static final double Y_UPPER_BOUND = -40;
    public static final double Y_LOWER_BOUND = 600.0;

    // Image Paths
    public static final String IMAGE_PATH_PREFIX = "/com/example/demo/images/";

    // UserPlane Configuration
    public static final String USER_PLANE_IMAGE  = "userplane.png";
    public static final int USER_INITIAL_HEALTH = 5;
    public static final double USER_SHIELD_X_OFFSET = 10.0;
    public static final double USER_SHIELD_Y_OFFSET = 15.0;
    public static final int USERPLANE_HEIGHT = 75;
    public static final int USERPLANE_VERTICAL_VELOCITY = 8;

    //UserProjectile Configuration
    public static final double USER_PROJECTILE_X_OFFSET = 110.0;
    public static final double USER_PROJECTILE_Y_OFFSET = 20.0;
    public static final String USER_PROJECTILE_IMAGE = "userfire.png";
    public static final int USER_PROJECTILE_HEIGHT = 125;
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 15;

    //Shield Configuration
    public static final String SHIELD_IMAGE =  "shield.png";
    public static final int SHIELD_SIZE = 100;
    public static final int MAX_FRAMES_WITH_SHIELD = 100;
    public static final int SHIELD_ITEM_HORIZONTAL_VELOCITY = -10;



    // Enemy Configuration
    public static final String ENEMY_PLANE_IMAGE  = "enemyplane.png";
    public static final int ENEMY_INITIAL_HEALTH = 1;
    public static final int ENEMY_PLANE_HEIGHT = 75;
    public static final double ENEMY_HORIZONTAL_VELOCITY = -6.0;
    public static final double ENEMY_PROJECTILE_X_OFFSET = -100.0;
    public static final double ENEMY_PROJECTILE_Y_OFFSET = 50.0;
    public static final double ENEMY_FIRE_RATE = 0.01;

    // Boss Configuration
    public static final String BOSS_IMAGE_NAME = "bossplane.png";
    public static final int BOSS_INITIAL_HEALTH = 100;
    public static final int BOSS_VERTICAL_VELOCITY = 8;
    public static final int BOSS_IMAGE_HEIGHT = 150;
    public static final double BOSS_FIRE_RATE = 0.04;
    public static final double BOSS_SHIELD_PROBABILITY = 0.01;
    public static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
    public static final int BOSS_MAX_FRAMES_WITH_SHIELD = 50;

    // Projectile Configuration
    public static final String BOSS_PROJECTILE_IMAGE = "fireball.png";
    public static final int BOSS_PROJECTILE_HEIGHT = 75;
    public static final int BOSS_PROJECTILE_HORIZONTAL_VELOCITY = -15;

    public static final String ENEMY_PROJECTILE_IMAGE = "enemyFire.png";
    public static final int ENEMY_PROJECTILE_HEIGHT = 40;
    public static final int ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -10;

    // Level Configuration
    public static final String LEVEL_ONE_BACKGROUND = IMAGE_PATH_PREFIX + "background1.jpg";
    public static final String LEVEL_TWO_BACKGROUND = IMAGE_PATH_PREFIX + "background2.jpg";
    public static final int TOTAL_ENEMIES = 5;
    public static final int TOTAL_ITEMS = 2;
    public static final int KILLS_TO_ADVANCE = 10;
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final double ITEM_SPAWN_PROBABILITY = 0.05;

    // UI Constants
    public static final String HEART_IMAGE_NAME = IMAGE_PATH_PREFIX + "heart.png";
    public static final int HEART_HEIGHT = 50;
    public static final int HEART_DISPLAY_X = 20;
    public static final int HEART_DISPLAY_Y = 20;
    public static final String GAME_OVER_IMAGE = IMAGE_PATH_PREFIX + "gameover.png";
    public static final int GAME_OVER_IMAGE_HEIGHT = 500;
    public static final int GAME_OVER_IMAGE_WIDTH = 600;
    public static final double KILL_COUNT_TEXT_X = SCREEN_WIDTH - 150;
    public static final double KILL_COUNT_TEXT_Y = 700;
    public static final String WIN_IMAGE = IMAGE_PATH_PREFIX + "youwin.png";
    public static final int WIN_IMAGE_HEIGHT = 500;
    public static final int WIN_IMAGE_WIDTH = 600;


    private Config() {
        // Prevent instantiation
    }
}
