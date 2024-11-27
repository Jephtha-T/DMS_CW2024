package com.example.demo.LevelControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.Actors.ActiveActorDestructible;
import com.example.demo.Config;
import com.example.demo.Actors.FighterPlane;
import com.example.demo.Actors.UserPlane;
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
		this.collisionManager = new CollisionManager(mRoot, mUser);
		collisionManager.setCurrentNumberOfEnemies(currentNumberOfEnemies);
		friendlyUnits.add(mUser);
		this.levelView = instantiateLevelView();
		soundManager = new SoundManager();
		soundManager.playBackgroundMusic(Config.BG_MUSIC_AUDIO);
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
		resetUserState();
		background.requestFocus();
		gameLoop.start(); // Correctly calls the instance method
		soundManager.playSoundEffect("levelStart");
	}

	private void resetUserState() {
		mUser.deactivateShield(); // Ensure the shield is turned off
	}


	private void updateScene() {
		spawnActors();
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

	private void spawnActors() {
		spawnEnemyUnits();
		spawnItems();
	}

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
		KeyCode kc = e.getCode();

		if (kc == KeyCode.ESCAPE) {
			togglePause(); // Toggle pause when Escape is pressed
			return;
		}

		if (paused) return; // Prevent other inputs if game is paused

		if (kc == KeyCode.UP) mUser.moveUp();
		else if (kc == KeyCode.DOWN) mUser.moveDown();
		else if (kc == KeyCode.SPACE) fireProjectile();
	}

	private void handleKeyReleased(KeyEvent e) {
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP || kc == KeyCode.DOWN) mUser.stop();
	}

	private void togglePause() {
		paused = !paused; // Toggle the pause state

		if (paused) {
			gameLoop.stop(); // Stop the game loop
			soundManager.pauseBackgroundMusic(); // Pause the music
			// Optional: Add visuals for paused state
			// showPauseOverlay();
		} else {
			gameLoop.start(); // Resume the game loop
			soundManager.resumeBackgroundMusic(); // Resume the music
			// Optional: Remove visuals for paused state
			// hidePauseOverlay();
		}
	}

	private void fireProjectile() {
		if (paused) return;
		ActiveActorDestructible projectile = mUser.fireProjectile();
		if (projectile != null) {
			mRoot.getChildren().add(projectile);
			userProjectiles.add(projectile);
			soundManager.playSoundEffect("gunshot");
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
		return levelTransitionInProgress;
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
		gameLoop.stop();
		levelView.showWinImage();
		soundManager.playSoundEffect("win");
	}

	protected void loseGame() {
		gameLoop.stop();
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

	public void stopGame() {
		gameLoop.stop();
	}
}
