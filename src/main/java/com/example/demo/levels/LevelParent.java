package com.example.demo.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.Config;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.controller.MyObservable;
import com.example.demo.controller.MyObserver;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Abstract class representing a parent level in the game.
 * Provides common functionality for all levels, including game loop management, collision handling, and user input.
 */
public abstract class LevelParent {

    private final MyObservable observable = new MyObservable(); // Observable for notifying observers
    private final double screenHeight; // Height of the screen
    private final double screenWidth; // Width of the screen
    private final double enemyMaximumYPosition; // Maximum Y position for enemies
    private final Group mRoot; // Root group for the level
    private final GameLoop gameLoop; // Game loop for updating the scene
    private final UserPlane mUser; // User's plane
    private final Scene scene; // Scene for the level
    private final ImageView background; // Background image for the level
    private final CollisionManager collisionManager; // Manager for handling collisions
    private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>(); // List of friendly units
    private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>(); // List of enemy units
    private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>(); // List of user projectiles
    private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>(); // List of enemy projectiles
    private final List<ActiveActorDestructible> items = new ArrayList<>(); // List of items
    private int currentNumberOfEnemies; // Current number of enemies
    private final LevelView levelView; // View for the level
    private boolean levelTransitionInProgress = false; // Flag for level transition
    private final SoundManager soundManager; // Manager for handling sounds
    private boolean paused = false; // Tracks if the game is paused
    private final PauseMenuManager pauseMenuManager; // Manager for the pause menu
    private boolean gameActive = true; // Tracks if the game is active

    /**
     * Constructor for LevelParent.
     * Initializes the level with the specified background image, screen dimensions, and player initial health.
     *
     * @param backgroundImageName the name of the background image
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param playerInitialHealth the initial health of the player
     */
    protected LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.mRoot = new Group();
        this.scene = new Scene(mRoot, screenWidth, screenHeight);
        this.gameLoop = new GameLoop(this::updateScene, Config.MILLISECOND_DELAY);
        this.mUser = new UserPlane(playerInitialHealth);
        this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - Config.SCREEN_HEIGHT_ADJUSTMENT;
        this.currentNumberOfEnemies = 0;
        this.pauseMenuManager = new PauseMenuManager(mRoot, this);
        this.collisionManager = new CollisionManager(mRoot, mUser);
        collisionManager.setCurrentNumberOfEnemies(currentNumberOfEnemies);
        friendlyUnits.add(mUser);
        this.levelView = instantiateLevelView();
        soundManager = new SoundManager();
    }

    /**
     * Adds an observer to the level.
     *
     * @param observer the observer to add
     */
    public void addObserver(MyObserver observer) {
        observable.addObserver(observer);
    }

    /**
     * Notifies all observers with the given argument.
     *
     * @param arg the argument to pass to the observers
     */
    protected void notifyObservers(Object arg) {
        observable.setChanged();
        observable.notifyObservers(arg);
    }

    /**
     * Abstract method to initialize friendly units.
     * Must be implemented by subclasses.
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Abstract method to check if the game is over.
     * Must be implemented by subclasses.
     */
    protected abstract void checkIfGameOver();

    /**
     * Abstract method to spawn enemy units.
     * Must be implemented by subclasses.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Abstract method to spawn items.
     * Must be implemented by subclasses.
     */
    protected abstract void spawnItems();

    /**
     * Abstract method to instantiate the level view.
     * Must be implemented by subclasses.
     *
     * @return the instantiated level view
     */
    protected abstract LevelView instantiateLevelView();

    /**
     * Initializes the scene for the level.
     * Sets up the background, friendly units, and UI elements.
     *
     * @return the initialized scene
     */
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        levelView.showKillCount();
        mRoot.getChildren().add(UserPlane.shieldImage);

        if (shouldShowHelpImage()) {
            levelView.showHelpImage();
            // Wait for Enter key to hide HelpImage and start the game
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    levelView.hideHelpImage();
                    startGame(); // Start the game after HelpImage is dismissed

                }
            });
        }

        return scene;
    }

    /**
     * Determines whether the help screen should be shown at the start.
     * By default, it is false. Specific levels can override this to true.
     *
     * @return true if HelpImage should be shown, false otherwise
     */
    protected boolean shouldShowHelpImage() {
        return false; // Default behavior: do not show HelpImage
    }

    /**
     * Starts the game.
     * Enables input, resets the user state, and starts the game loop.
     */
    public void startGame() {
        gameActive = true; // Enable input
        resetUserState();
        background.requestFocus();
        gameLoop.start();
        soundManager.playSoundEffect("levelStart");
        soundManager.playBackgroundMusic(Config.BG_MUSIC_AUDIO);
    }

    /**
     * Resets the user state.
     * Deactivates the shield of the user plane.
     */
    private void resetUserState() {
        mUser.deactivateShield(); // Ensure the shield is turned off
    }

    /**
     * Updates the scene.
     * Spawns enemies and items, updates all actors, handles collisions, and refreshes the UI.
     */
    public void updateScene() {
        spawnEnemiesAndItems();
        updateAllActors();

        // Accumulate destroyed enemies
        int enemiesDestroyed = collisionManager.handleCollisions(enemyUnits,
                userProjectiles, enemyProjectiles, items
        );

        // Update kill count directly
        if (enemiesDestroyed > 0) {
            soundManager.playSoundEffect("explosion");
            mUser.incrementKillCount(enemiesDestroyed);
            levelView.updateKillCount(mUser.getNumberOfKills());
        }

        cleanupActors();
        refreshUI();
        checkIfGameOver();
    }

    /**
     * Abstract method to spawn enemies and items.
     * Must be implemented by subclasses.
     */
    protected abstract void spawnEnemiesAndItems();

    /**
     * Updates all actors in the level.
     * Updates friendly units, enemy units, user projectiles, enemy projectiles, and items.
     */
    private void updateAllActors() {
        updateActors();
        generateEnemyFire();
    }

    /**
     * Cleans up actors in the level.
     * Removes all destroyed actors and updates the number of enemies.
     */
    private void cleanupActors() {
        removeAllDestroyedActors();
        updateNumberOfEnemies();
    }

    /**
     * Refreshes the UI.
     * Updates the kill count and level view.
     */
    private void refreshUI() {
        updateKillCount();
        levelView.showKillCount();
        updateLevelView();
    }

    /**
     * Initializes the background for the level.
     * Sets the background image and key event handlers.
     */
    private void initializeBackground() {
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setFocusTraversable(true);
        background.setOnKeyPressed(this::handleKeyPressed);
        background.setOnKeyReleased(this::handleKeyReleased);
        mRoot.getChildren().add(background);
    }

    /**
     * Handles key pressed events.
     * Processes user input for moving the user plane, firing projectiles, pausing & returning to main menu after the game is over
     *
     * @param e the key event
     */
    void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (!gameActive && kc == KeyCode.ENTER) {
            LevelManager.getInstance().loadMainMenu();
            return;
        }
        if (kc == KeyCode.ESCAPE) {
            togglePause();
            return;
        }
        if (paused) return;

        if (kc == KeyCode.UP) mUser.moveUp();
        else if (kc == KeyCode.DOWN) mUser.moveDown();
        else if (kc == KeyCode.SPACE) fireProjectile();
    }

    /**
     * Handles key released events.
     * Stops the user plane when the up or down key is released.
     *
     * @param e the key event
     */
    private void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.UP || kc == KeyCode.DOWN) mUser.stop();
    }

    /**
     * Toggles the pause state of the game.
     * Pauses or resumes the game loop and background music.
     */
    void togglePause() {
        if (!gameActive) return; // Prevent pausing/resuming if the game is over

        paused = !paused;

        if (paused) {
            gameLoop.stop(); // Stop the game loop
            soundManager.pauseBackgroundMusic(); // Pause music
            pauseMenuManager.showPauseMenu(); // Show the pause menu
        } else {
            pauseMenuManager.hidePauseMenu(); // Hide the pause menu
            gameLoop.start(); // Restart the game loop
            soundManager.resumeBackgroundMusic(); // Resume music
        }
    }

    /**
     * Fires multiple projectiles.
     * Adds the projectiles to the scene and plays the firing sound.
     *
     * @param projectiles the list of projectiles to fire
     */
    private void fireProjectiles(List<ActiveActorDestructible> projectiles) {
        if (paused || projectiles == null || projectiles.isEmpty()) return;

        for (ActiveActorDestructible projectile : projectiles) {
            mRoot.getChildren().add(projectile); // Add to the scene
            userProjectiles.add(projectile);     // Track the projectile
        }

        soundManager.playSoundEffect("gunshot"); // Play the firing sound
    }

    /**
     * Fires a single projectile or multiple projectiles if multi-shot is enabled.
     * Adds the projectiles to the scene and plays the firing sound.
     */
    private void fireProjectile() {
        if (paused) return;

        // Check if multi-shot is enabled
        if (mUser.isMultiShotEnabled()) {
            // Use the new method for handling multiple projectiles
            List<ActiveActorDestructible> projectiles = mUser.fireMultiShot();
            fireProjectiles(projectiles);
        } else {
            // Single projectile firing
            ActiveActorDestructible projectile = mUser.fireProjectile();
            if (projectile != null) {
                mRoot.getChildren().add(projectile); // Add to the scene
                userProjectiles.add(projectile);     // Track the projectile
                soundManager.playSoundEffect("gunshot"); // Play the firing sound
            }
        }
    }

    /**
     * Generates enemy fire.
     * Spawns projectiles for each enemy unit.
     */
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns an enemy projectile.
     * Adds the projectile to the scene and tracks it.
     *
     * @param projectile the projectile to spawn
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            mRoot.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates all actors in the level.
     * Calls the updateActor method for each actor.
     */
    private void updateActors() {
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
        items.forEach(ActiveActorDestructible::updateActor);
    }

    /**
     * Removes all destroyed actors from the level.
     * Calls the removeDestroyedActors method for each list of actors.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedActors(items);
    }

    /**
     * Removes destroyed actors from the given list.
     * Triggers explosions for destroyed actors and removes them from the scene.
     *
     * @param actors the list of actors to remove
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .toList();
        destroyedActors.forEach(collisionManager::handleEnemyDestruction); // Trigger explosions
        mRoot.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Checks if a level transition is in progress.
     *
     * @return true if a level transition is in progress, false otherwise
     */
    protected boolean isTransitionInProgress() {
        return !levelTransitionInProgress;
    }

    /**
     * Starts a level transition.
     * Sets the levelTransitionInProgress flag to true.
     */
    protected void startLevelTransition() {
        levelTransitionInProgress = true;
    }

    /**
     * Adds an item to the level.
     * Sets the item to visible and adds it to the scene and item list.
     *
     * @param item the item to add
     */
    protected void addItem(ActiveActorDestructible item) {
        item.setVisible(true); // Explicitly setting visibility
        items.add(item);
        getRoot().getChildren().add(item);
    }

    /**
     * Updates the level view.
     * Removes hearts from the level view based on the user's health.
     */
    private void updateLevelView() {
        levelView.removeHearts(mUser.getHealth());
    }

    /**
     * Updates the kill count in the level view.
     * Only updates the UI.
     */
    private void updateKillCount() {
        levelView.updateKillCount(mUser.getNumberOfKills()); // Only update UI
    }

    /**
     * Handles the win condition for the level.
     * Stops the game, shows the win image, and plays the win sound effect.
     */
    protected void winGame() {
        stopGame();
        levelView.showWinImage();
        soundManager.playSoundEffect("win");
    }

    /**
     * Handles the lose condition for the level.
     * Stops the game, shows the game over image, and plays the game over sound effect.
     */
    protected void loseGame() {
        stopGame();
        levelView.showGameOverImage();
        soundManager.playSoundEffect("gameOver");
    }

    /**
     * Gets the user plane.
     *
     * @return the user plane
     */
    protected UserPlane getUser() {
        return mUser;
    }

    /**
     * Gets the root group.
     *
     * @return the root group
     */
    protected Group getRoot() {
        return mRoot;
    }

    /**
     * Gets the current number of enemies.
     *
     * @return the current number of enemies
     */
    public int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Gets the current number of items.
     *
     * @return the current number of items
     */
    public int getCurrentNumberOfItems() {
        return items.size();
    }

    /**
     * Adds an enemy unit to the level.
     * Adds the enemy to the scene and enemy list.
     *
     * @param enemy the enemy unit to add
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        mRoot.getChildren().add(enemy);
    }

    /**
     * Gets the maximum Y position for enemies.
     *
     * @return the maximum Y position for enemies
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the screen height.
     *
     * @return the screen height
     */
    protected double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Checks if the user plane is destroyed.
     *
     * @return true if the user plane is destroyed, false otherwise
     */
    protected boolean userIsDestroyed() {
        return mUser.isDestroyed();
    }

    /**
     * Updates the number of enemies.
     * Sets the current number of enemies to the size of the enemy list.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }

    /**
     * Abstract method to get the number of kills required to advance to the next level.
     * Must be implemented by subclasses.
     *
     * @return the number of kills required to advance
     */
    protected abstract int getKillsToAdvance();

    /**
     * Stops the game.
     * Stops the game loop, stops the background music, and clears all entities.
     */
    public void stopGame() {
        gameLoop.stop();
        soundManager.stopBackgroundMusic();

        // Clear all entities to release memory
        friendlyUnits.clear();
        enemyUnits.clear();
        userProjectiles.clear();
        enemyProjectiles.clear();
        items.clear();

        // Additional cleanup if required
        gameActive = false;  // Indicate game has stopped
    }
}