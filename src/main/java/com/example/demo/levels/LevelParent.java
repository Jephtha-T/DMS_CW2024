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


public abstract class LevelParent{

	private final MyObservable observable = new MyObservable();

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final Group mRoot;
	private final GameLoop gameLoop;
	private final UserPlane mUser;
	private final Scene scene;
	private final ImageView background;
	private final CollisionManager collisionManager;
	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> items = new ArrayList<>();
	private int currentNumberOfEnemies;
	private final LevelView levelView;
	private boolean levelTransitionInProgress = false;
	private final SoundManager soundManager;
	private boolean paused = false; // Tracks if the game is paused
	private final PauseMenuManager pauseMenuManager;
	private boolean gameActive = true;





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

	public void addObserver(MyObserver observer) {
		observable.addObserver(observer);
	}

	protected void notifyObservers(Object arg) {
		observable.setChanged();
		observable.notifyObservers(arg);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();
	protected abstract void spawnItems();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillCount();
		mRoot.getChildren().add(UserPlane.shieldImage);
		return scene;
	}

	public void startGame() {
		gameActive = true; // Enable input
		resetUserState();
		background.requestFocus();
		gameLoop.start();
		soundManager.playSoundEffect("levelStart");
		soundManager.playBackgroundMusic(Config.BG_MUSIC_AUDIO);
	}


	private void resetUserState() {
		mUser.deactivateShield(); // Ensure the shield is turned off
	}


	private void updateScene() {
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

    protected abstract void spawnEnemiesAndItems();

	private void updateAllActors() {
		updateActors();
		generateEnemyFire();
	}

	private void cleanupActors() {
		removeAllDestroyedActors();
		updateNumberOfEnemies();
	}

	private void refreshUI() {
		updateKillCount();
		levelView.showKillCount();
		updateLevelView();
	}


	private void initializeBackground() {
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setFocusTraversable(true);
		background.setOnKeyPressed(this::handleKeyPressed);
		background.setOnKeyReleased(this::handleKeyReleased);
		mRoot.getChildren().add(background);
	}

	private void handleKeyPressed(KeyEvent e) {
		if (!gameActive) return; // Ignore inputs if the game is not active

		KeyCode kc = e.getCode();
		if (kc == KeyCode.ESCAPE) {
			togglePause();
			return;
		}
		if (paused) return;

		if (kc == KeyCode.UP) mUser.moveUp();
		else if (kc == KeyCode.DOWN) mUser.moveDown();
		else if (kc == KeyCode.SPACE) fireProjectile();
	}

	private void handleKeyReleased(KeyEvent e) {
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP || kc == KeyCode.DOWN) mUser.stop();
	}

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

	private void fireProjectiles(List<ActiveActorDestructible> projectiles) {
		if (paused || projectiles == null || projectiles.isEmpty()) return;

		for (ActiveActorDestructible projectile : projectiles) {
			mRoot.getChildren().add(projectile); // Add to the scene
			userProjectiles.add(projectile);     // Track the projectile
		}

		soundManager.playSoundEffect("gunshot"); // Play the firing sound
	}



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


	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			mRoot.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}


	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
		items.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(items);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.toList();
		destroyedActors.forEach(collisionManager::handleEnemyDestruction); // Trigger explosions
		mRoot.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	protected boolean isTransitionInProgress() {
		return !levelTransitionInProgress;
	}

	protected void startLevelTransition() {
		levelTransitionInProgress = true;
	}

	protected void addItem(ActiveActorDestructible item) {
		item.setVisible(true); // Explicitly setting visibility
		items.add(item);
		getRoot().getChildren().add(item);
	}

	private void updateLevelView() {
		levelView.removeHearts(mUser.getHealth());

	}

	private void updateKillCount() {
		levelView.updateKillCount(mUser.getNumberOfKills()); // Only update UI
	}

	protected void winGame() {
		stopGame();
		levelView.showWinImage();
		soundManager.playSoundEffect("win");
	}

	protected void loseGame() {
		stopGame();
		levelView.showGameOverImage();
		soundManager.playSoundEffect("gameOver");
	}


	protected UserPlane getUser() {
		return mUser;
	}

	protected Group getRoot() {
		return mRoot;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected int getCurrentNumberOfItems() {
		return items.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		mRoot.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}
	protected double getScreenHeight() {
		return screenHeight;
	}

	protected boolean userIsDestroyed() {
		return mUser.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected abstract int getKillsToAdvance();

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
