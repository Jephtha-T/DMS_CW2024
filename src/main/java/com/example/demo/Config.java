package com.example.demo;

public final class Config {

    // General Game Configuration
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    public static final int MILLISECOND_DELAY = 50;
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 50.0;
    public static final double Y_UPPER_BOUND = -40;
    public static final double Y_LOWER_BOUND = 700.0;
    public static final String MAIN_MENU_FXML = "/com/example/demo/MainMenu.fxml";
    public static final String PAUSE_MENU_FXML = "/com/example/demo/PauseMenu.fxml";
    public static final String FONT_TTF = "/com/example/demo/RetroGaming.ttf";

    // Image Paths
    public static final String IMAGE_PATH_PREFIX = System.getProperty("image.path.prefix", "/com/example/demo/images/");
    public static final String AUDIO_PATH_PREFIX = System.getProperty("audio.path.prefix", "/com/example/demo/Audio/");


    //Audio
    public static final String EXPLOSION_AUDIO = AUDIO_PATH_PREFIX + "ArcadeExplosion.wav";
    public static final String GAME_OVER_AUDIO = AUDIO_PATH_PREFIX + "ArcadeGameOver.wav";
    public static final String GUNSHOT_AUDIO = AUDIO_PATH_PREFIX + "ArcadeGunshot.mp3";
    public static final String START_AUDIO = AUDIO_PATH_PREFIX + "ArcadeLevelStart.wav";
    public static final String WIN_AUDIO = AUDIO_PATH_PREFIX + "ArcadeWin.wav";
    public static final String BG_MUSIC_AUDIO = AUDIO_PATH_PREFIX + "SciFiBgMusic.mp3";

    // UserPlane Configuration
    public static final String USER_PLANE_IMAGE  = "userplane.png";
    public static final int USER_INITIAL_HEALTH = 5;
    public static final double USER_SHIELD_X_OFFSET = -20.0;
    public static final double USER_SHIELD_Y_OFFSET = 0.0;
    public static final int USERPLANE_HEIGHT = 75;
    public static final int USERPLANE_VERTICAL_VELOCITY = 8;
    public static final int FIRE_COOLDOWN = 200;

    //UserProjectile Configuration
    public static final double USER_PROJECTILE_X_OFFSET = 110.0;
    public static final double USER_PROJECTILE_Y_OFFSET = 20.0;
    public static final String USER_PROJECTILE_IMAGE = "userfire.png";
    public static final int USER_PROJECTILE_HEIGHT = 30;
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 15;


    //Item Configuration
    public static final int ITEM_HORIZONTAL_VELOCITY = -10;
    public static final String MULTISHOT_IMAGE =  "multishot.png";
    public static final int MULTISHOT_SIZE = 50;
    public static final String HEALTH_IMAGE =  IMAGE_PATH_PREFIX + "heart.png";
    public static final int HEALTH_SIZE = 50;
    public static final String SHIELD_IMAGE =  "shield.png";
    public static final int SHIELD_SIZE = 50;

    //Shield Configuration
    public static final String SHIELD_EFFECT=  "shield_effect.png";
    public static final int MAX_FRAMES_WITH_SHIELD = 100;

    //Explosion Configuration
    public static final String EXPLOSION_SPRITESHEET_PATH = IMAGE_PATH_PREFIX + "SpriteSheets/explosion_spritesheet.png";
    public static final int EXPLOSION_FRAME_WIDTH = 517;
    public static final int EXPLOSION_FRAME_HEIGHT = 517;
    public static final int EXPLOSION_TOTAL_FRAMES = 30;
    public static final double EXPLOSION_FRAME_DURATION = 0.05; // Seconds per frame


    // Enemy Configuration
    public static final String ENEMY_PLANE_IMAGE  = "enemyplane.png";
    public static final int ENEMY_INITIAL_HEALTH = 1;
    public static final int ENEMY_PLANE_HEIGHT = 75;
    public static final double ENEMY_HORIZONTAL_VELOCITY = -6.0;
    public static final String CHARGE_PLANE_IMAGE  = "chargeplane.png";
    public static final int CHARGE_INITIAL_HEALTH = 2;
    public static final int CHARGE_PLANE_HEIGHT = 65;
    public static final double CHARGE_HORIZONTAL_VELOCITY = -12.0;
    public static final double BOMBER_VERTICAL_VELOCITY = 3.0;
    public static final String BOMBER_PLANE_IMAGE  = "bomberplane.png";
    public static final int BOMBER_INITIAL_HEALTH = 2;
    public static final int BOMBER_PLANE_HEIGHT = 75;
    public static final double ENEMY_PROJECTILE_X_OFFSET = -100.0;
    public static final double ENEMY_PROJECTILE_Y_OFFSET = 10.0;
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
    public static final String ENEMY_PROJECTILE_IMAGE = "enemyfire.png";
    public static final int ENEMY_PROJECTILE_HEIGHT = 30;
    public static final int ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -10;
    public static final String BOMB_PROJECTILE_IMAGE = "bomberfire.png";
    public static final int BOMB_PROJECTILE_HEIGHT = 40;
    public static final int BOMB_PROJECTILE_HORIZONTAL_VELOCITY = -8;

    // Level Configuration
    public static final String LEVEL_ONE_CLASS = "com.example.demo.levels.LevelOne";
    public static final String LEVEL_TWO_CLASS = "com.example.demo.levels.LevelTwo";
    public static final String LEVEL_THREE_CLASS = "com.example.demo.levels.LevelThree";
    public static final String LEVEL_ENDLESS_CLASS = "com.example.demo.levels.LevelEndless";
    public static final String LEVEL_ONE_BACKGROUND = IMAGE_PATH_PREFIX + "background1.png";
    public static final String LEVEL_TWO_BACKGROUND = IMAGE_PATH_PREFIX + "background2.png";
    public static final int TOTAL_ENEMIES = 5;
    public static final int TOTAL_ITEMS = 2;
    public static final int KILLS_TO_ADVANCE_LEVELONE = 10;
    public static final int KILLS_TO_ADVANCE_LEVELTWO = 10;
    public static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    public static final double ITEM_SPAWN_PROBABILITY = 0.03;

    // UI Constants

    public static final int HEART_HEIGHT = 50;
    public static final int HEART_DISPLAY_X = 20;
    public static final int HEART_DISPLAY_Y = 20;
    public static final String GAME_OVER_IMAGE = IMAGE_PATH_PREFIX + "gameover.png";
    public static final int GAME_OVER_IMAGE_HEIGHT = 500;
    public static final int GAME_OVER_IMAGE_WIDTH = 600;
    public static final double KILL_COUNT_TEXT_X = SCREEN_WIDTH - 150.0;
    public static final double KILL_COUNT_TEXT_Y = 50.0;
    public static final String WIN_IMAGE = IMAGE_PATH_PREFIX + "youwin.png";
    public static final int WIN_IMAGE_HEIGHT = 500;
    public static final int WIN_IMAGE_WIDTH = 600;



    private Config() {
        // Prevent instantiation
    }
}
