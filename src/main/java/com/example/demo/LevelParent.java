package com.example.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.example.demo.controller.MyObservable;
import com.example.demo.controller.MyObserver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


public abstract class LevelParent{

	private final MyObservable observable = new MyObservable();

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private boolean gameActive = true;

	private final Group root;
	private final GameLoop gameLoop;
	private final UserPlane user;
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

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.gameLoop = new GameLoop(this::updateScene, MILLISECOND_DELAY);
		this.user = new UserPlane(playerInitialHealth);
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.collisionManager = new CollisionManager(root, user);
		collisionManager.setCurrentNumberOfEnemies(currentNumberOfEnemies);
		friendlyUnits.add(user);
		System.out.println("Kill Count Text exists: " + root.getChildren().contains(levelView.killCountText));

	}

	public void addObserver(MyObserver observer) {
		observable.addObserver(observer);
	}

	public void removeObserver(MyObserver observer) {
		observable.removeObserver(observer);
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
		root.getChildren().add(UserPlane.shieldImage);
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		gameLoop.start(); // Correctly calls the instance method
	}


	private void updateScene() {
		spawnActors();
		updateAllActors();

		// Accumulate destroyed enemies
		int enemiesDestroyed = collisionManager.handleCollisions(
				friendlyUnits,
				enemyUnits,
				userProjectiles,
				enemyProjectiles,
				items
		);

		// Update kill count directly
		if (enemiesDestroyed > 0) {
			user.incrementKillCount(enemiesDestroyed);
			System.out.println("Enemies destroyed: " + enemiesDestroyed);
			System.out.println("Total kills: " + user.getNumberOfKills());
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
		root.getChildren().add(background);
	}

	private void handleKeyPressed(KeyEvent e) {
		if (!gameActive) return;
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP) user.moveUp();
		else if (kc == KeyCode.DOWN) user.moveDown();
		else if (kc == KeyCode.SPACE) fireProjectile();
	}

	private void handleKeyReleased(KeyEvent e) {
		KeyCode kc = e.getCode();
		if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
	}

	private void fireProjectile() {
		if (!gameActive) return;
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile != null) {
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
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
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
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
		levelView.removeHearts(user.getHealth());

	}

	private void updateKillCount() {
		levelView.updateKillCount(user.getNumberOfKills()); // Only update UI
	}

	protected void winGame() {
		gameLoop.stop();
		levelView.showWinImage();
		gameActive = false;
	}

	protected void loseGame() {
		gameLoop.stop();
		levelView.showGameOverImage();
		gameActive = false;
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected int getCurrentNumberOfItems() {
		return items.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
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
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	public void stopGame() {
		gameLoop.stop();
	}
}
