package com.example.demo;

/**
 * The {@code Config} class contains all the constants and configuration values
 * used throughout the game. This includes settings for screen dimensions, game
 * audio, images, player and enemy configurations, as well as level and UI settings.
 * All values in this class are static and final, making them accessible globally
 * throughout the application and ensuring they are immutable.
 *
 * This class is designed to centralize all the constant configurations and prevent
 * magic numbers or hardcoded values from appearing elsewhere in the code.
 *
 * It contains the following main sections:
 * <ul>
 *   <li>General Game Configuration</li>
 *   <li>Audio</li>
 *   <li>User Plane Configuration</li>
 *   <li>User Projectile Configuration</li>
 *   <li>Item Configuration</li>
 *   <li>Shield Configuration</li>
 *   <li>Explosion Configuration</li>
 *   <li>Enemy Configuration</li>
 *   <li>Boss Configuration</li>
 *   <li>Projectile Configuration</li>
 *   <li>Level Configuration</li>
 *   <li>UI Constants</li>
 * </ul>
 *
 * This class cannot be instantiated as its constructor is private.
 */
public final class Config {

    // General Game Configuration
    /** The title of the game */
    public static final String TITLE = "Sky Battle";

    /** The width of the game screen */
    public static final int SCREEN_WIDTH = 1300;

    /** The height of the game screen */
    public static final int SCREEN_HEIGHT = 750;

    /** The delay in milliseconds between game updates */
    public static final int MILLISECOND_DELAY = 50;

    /** The adjustment factor to the screen height for positioning */
    public static final double SCREEN_HEIGHT_ADJUSTMENT = 50.0;

    /** The upper bound for Y-axis movement */
    public static final double Y_UPPER_BOUND = 0;

    /** The lower bound for Y-axis movement */
    public static final double Y_LOWER_BOUND = 700.0;

    /** Path to the main menu FXML file */
    public static final String MAIN_MENU_FXML = "/com/example/demo/MainMenu.fxml";

    /** Path to the pause menu FXML file */
    public static final String PAUSE_MENU_FXML = "/com/example/demo/PauseMenu.fxml";

    /** Path to the custom font used in the game */
    public static final String FONT_TTF = "/com/example/demo/RetroGaming.ttf";

    // Image Paths
    /** Prefix for the path to image files */
    public static final String IMAGE_PATH_PREFIX = System.getProperty("image.path.prefix", "/com/example/demo/images/");

    /** Prefix for the path to audio files */
    public static final String AUDIO_PATH_PREFIX = System.getProperty("audio.path.prefix", "/com/example/demo/Audio/");

    // Audio
    /** Path to the explosion sound file */
    public static final String EXPLOSION_AUDIO = AUDIO_PATH_PREFIX + "ArcadeExplosion.wav";

    /** Path to the game over sound file */
    public static final String GAME_OVER_AUDIO = AUDIO_PATH_PREFIX + "ArcadeGameOver.wav";

    /** Path to the gunshot sound file */
    public static final String GUNSHOT_AUDIO = AUDIO_PATH_PREFIX + "ArcadeGunshot.mp3";

    /** Path to the level start sound file */
    public static final String START_AUDIO = AUDIO_PATH_PREFIX + "ArcadeLevelStart.wav";

    /** Path to the win sound file */
    public static final String WIN_AUDIO = AUDIO_PATH_PREFIX + "ArcadeWin.wav";

    /** Path to the background music audio file */
    public static final String BG_MUSIC_AUDIO = AUDIO_PATH_PREFIX + "SciFiBgMusic.mp3";

    // User Plane Configuration
    /** The image of the user's plane */
    public static final String USER_PLANE_IMAGE  = "userplane.png";

    /** The initial health of the user plane */
    public static final int USER_INITIAL_HEALTH = 5;

    /** X offset for the user's shield */
    public static final double USER_SHIELD_X_OFFSET = -20.0;

    /** Y offset for the user's shield */
    public static final double USER_SHIELD_Y_OFFSET = 0.0;

    /** Height of the user plane */
    public static final int USERPLANE_HEIGHT = 75;

    /** Vertical velocity of the user plane */
    public static final int USERPLANE_VERTICAL_VELOCITY = 8;

    /** The cooldown time in milliseconds before the user can fire again */
    public static final int FIRE_COOLDOWN = 200;

    // User Projectile Configuration
    /** X offset for user projectiles */
    public static final double USER_PROJECTILE_X_OFFSET = 110.0;

    /** Y offset for user projectiles */
    public static final double USER_PROJECTILE_Y_OFFSET = 20.0;

    /** The image of the user projectile */
    public static final String USER_PROJECTILE_IMAGE = "userfire.png";

    /** Height of the user projectile */
    public static final int USER_PROJECTILE_HEIGHT = 30;

    /** Horizontal velocity of the user projectile */
    public static final int USER_PROJECTILE_HORIZONTAL_VELOCITY = 15;

    // Item Configuration
    /** Horizontal velocity of items */
    public static final int ITEM_HORIZONTAL_VELOCITY = -10;

    /** Image of the multi-shot item */
    public static final String MULTISHOT_IMAGE =  "multishot.png";

    /** Size of the multi-shot item */
    public static final int MULTISHOT_SIZE = 50;

    /** Image of the heart item */
    public static final String HEART_IMAGE =  IMAGE_PATH_PREFIX + "heart.png";

    /** Image of the health item */
    public static final String HEALTH_ITEM_IMAGE = "health.png";

    /** Size of the health item */
    public static final int HEALTH_SIZE = 50;

    /** Image of the shield item */
    public static final String SHIELD_IMAGE =  "shield.png";

    /** Size of the shield item */
    public static final int SHIELD_SIZE = 50;

    // Shield Configuration
    /** Image of the shield effect */
    public static final String SHIELD_EFFECT=  "shield_effect.png";

    /** Maximum number of frames a shield can stay active */
    public static final int MAX_FRAMES_WITH_SHIELD = 100;

    // Explosion Configuration
    /** Path to the explosion spritesheet image */
    public static final String EXPLOSION_SPRITESHEET_PATH = IMAGE_PATH_PREFIX + "SpriteSheets/explosion_spritesheet.png";

    /** Width of a single frame in the explosion spritesheet */
    public static final int EXPLOSION_FRAME_WIDTH = 517;

    /** Height of a single frame in the explosion spritesheet */
    public static final int EXPLOSION_FRAME_HEIGHT = 517;

    /** Total number of frames in the explosion spritesheet */
    public static final int EXPLOSION_TOTAL_FRAMES = 30;

    /** Duration of each explosion frame in seconds */
    public static final double EXPLOSION_FRAME_DURATION = 0.05;

    // Enemy Configuration
    /** Image of the enemy plane */
    public static final String ENEMY_PLANE_IMAGE  = "enemyplane.png";

    /** Initial health of the enemy plane */
    public static final int ENEMY_INITIAL_HEALTH = 1;

    /** Height of the enemy plane */
    public static final int ENEMY_PLANE_HEIGHT = 75;

    /** Horizontal velocity of the enemy plane */
    public static final double ENEMY_HORIZONTAL_VELOCITY = -6.0;

    /** Image of the charge enemy plane */
    public static final String CHARGE_PLANE_IMAGE  = "chargeplane.png";

    /** Initial health of the charge enemy plane */
    public static final int CHARGE_INITIAL_HEALTH = 2;

    /** Height of the charge enemy plane */
    public static final int CHARGE_PLANE_HEIGHT = 65;

    /** Horizontal velocity of the charge enemy plane */
    public static final double CHARGE_HORIZONTAL_VELOCITY = -12.0;

    /** Vertical velocity of the bomber enemy plane */
    public static final double BOMBER_VERTICAL_VELOCITY = 3.0;

    /** Image of the bomber enemy plane */
    public static final String BOMBER_PLANE_IMAGE  = "bomberplane.png";

    /** Initial health of the bomber enemy plane */
    public static final int BOMBER_INITIAL_HEALTH = 2;

    /** Height of the bomber enemy plane */
    public static final int BOMBER_PLANE_HEIGHT = 75;

    /** X offset for enemy projectiles */
    public static final double ENEMY_PROJECTILE_X_OFFSET = -100.0;

    /** Y offset for enemy projectiles */
    public static final double ENEMY_PROJECTILE_Y_OFFSET = 10.0;

    /** Fire rate of enemy projectiles */
    public static final double ENEMY_FIRE_RATE = 0.01;

    // Boss Configuration
    /** Image of the boss plane */
    public static final String BOSS_IMAGE_NAME = "bossplane.png";

    /** Initial health of the boss plane */
    public static final int BOSS_INITIAL_HEALTH = 100;

    /** Vertical velocity of the boss plane */
    public static final int BOSS_VERTICAL_VELOCITY = 8;

    /** Height of the boss plane */
    public static final int BOSS_IMAGE_HEIGHT = 150;

    /** Fire rate of the boss projectiles */
    public static final double BOSS_FIRE_RATE = 0.02;

    /** Probability that the boss will activate a shield */
    public static final double BOSS_SHIELD_PROBABILITY = 0.01;

    /** Y offset for boss projectiles */
    public static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;

    /** Maximum number of frames the boss can stay shielded */
    public static final int BOSS_MAX_FRAMES_WITH_SHIELD = 50;

    // Projectile Configuration
    /** Image of the boss projectile */
    public static final String BOSS_PROJECTILE_IMAGE = "fireball.png";

    /** Height of the boss projectile */
    public static final int BOSS_PROJECTILE_HEIGHT = 75;

    /** Horizontal velocity of the boss projectile */
    public static final int BOSS_PROJECTILE_HORIZONTAL_VELOCITY = -15;

    /** Image of the enemy projectile */
    public static final String ENEMY_PROJECTILE_IMAGE = "enemyfire.png";

    /** Height of the enemy projectile */
    public static final int ENEMY_PROJECTILE_HEIGHT = 30;

    /** Horizontal velocity of the enemy projectile */
    public static final int ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -10;

    /** Image of the bomber projectile */
    public static final String BOMB_PROJECTILE_IMAGE = "bomberfire.png";

    /** Height of the bomber projectile */
    public static final int BOMB_PROJECTILE_HEIGHT = 40;

    /** Horizontal velocity of the bomber projectile */
    public static final int BOMB_PROJECTILE_HORIZONTAL_VELOCITY = -8;

    // Level Configuration
    /** Path to the Level One class */
    public static final String LEVEL_ONE_CLASS = "com.example.demo.levels.LevelOne";

    /** Path to the Level Two class */
    public static final String LEVEL_TWO_CLASS = "com.example.demo.levels.LevelTwo";

    /** Path to the Level Three class */
    public static final String LEVEL_THREE_CLASS = "com.example.demo.levels.LevelThree";

    /** Path to the Endless Level class */
    public static final String LEVEL_ENDLESS_CLASS = "com.example.demo.levels.LevelEndless";

    /** Background image for Level One */
    public static final String LEVEL_ONE_BACKGROUND = IMAGE_PATH_PREFIX + "background1.png";

    /** Background image for Level Two */
    public static final String LEVEL_TWO_BACKGROUND = IMAGE_PATH_PREFIX + "background2.png";

    /** Total number of enemies to spawn in each level */
    public static final int TOTAL_ENEMIES = 10;

    /** Total number of items to spawn in each level */
    public static final int TOTAL_ITEMS = 5;

    /** Number of kills required to advance from Level One */
    public static final int KILLS_TO_ADVANCE_LEVELONE = 10;

    /** Number of kills required to advance from Level Two */
    public static final int KILLS_TO_ADVANCE_LEVELTWO = 10;

    // UI Constants
    /** Height of the heart icon displayed in the UI */
    public static final int HEART_HEIGHT = 50;

    /** X position of the heart icon in the UI */
    public static final int HEART_DISPLAY_X = 20;

    /** Y position of the heart icon in the UI */
    public static final int HEART_DISPLAY_Y = 20;

    /** Path to the game over image */
    public static final String GAME_OVER_IMAGE = IMAGE_PATH_PREFIX + "gameover.png";

    /** Height of the game over image */
    public static final int GAME_OVER_IMAGE_HEIGHT = 800;

    /** Width of the game over image */
    public static final int GAME_OVER_IMAGE_WIDTH = 1400;

    /** X position of the game over image */
    public static final int GAME_OVER_X_POSITION = 0;

    /** Y position of the game over image */
    public static final int GAME_OVER_Y_POSITION = 0;

    /** X position for displaying the kill count in the UI */
    public static final double KILL_COUNT_TEXT_X = SCREEN_WIDTH - 150.0;

    /** Y position for displaying the kill count in the UI */
    public static final double KILL_COUNT_TEXT_Y = 50.0;

    /** Path to the win image */
    public static final String WIN_IMAGE = IMAGE_PATH_PREFIX + "youwin.png";

    /** Height of the win image */
    public static final int WIN_IMAGE_HEIGHT = 800;

    /** Width of the win image */
    public static final int WIN_IMAGE_WIDTH = 1400;

    /** X position of the win image */
    public static final int WIN_IMAGE_X_POSITION = 0;

    /** Y position of the win image */
    public static final int WIN_IMAGE_Y_POSITION = 0;

    /** Path to the help image */
    public static final String HELP_IMAGE = IMAGE_PATH_PREFIX + "help.png";

    /** Height of the help image */
    public static final int HELP_IMAGE_HEIGHT = 800;

    /** Width of the help image */
    public static final int HELP_IMAGE_WIDTH = 1400;

    /** X position of the help image */
    public static final int HELP_X_POSITION = 0;

    /** Y position of the help image */
    public static final int HELP_Y_POSITION = 0;

    // Private constructor to prevent instantiation
    private Config() {
        // Prevent instantiation
    }
}