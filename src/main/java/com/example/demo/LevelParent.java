package com.example.demo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.example.demo.controller.MyObservable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


public abstract class LevelParent extends MyObservable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private boolean gameActive = true;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> userProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> items = new ArrayList<>();


	private int currentNumberOfEnemies;
	private final LevelView levelView;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
		System.out.println("Kill Count Text exists: " + root.getChildren().contains(levelView.killCountText));

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
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		if (getUser().getNumberOfKills() >= 10 && !hasChanged()) {
			System.out.println("Moving to next level: " + levelName);
			setChanged(); // Mark the observable as changed
			notifyObservers(levelName); // Notify the observer (Controller)
			timeline.stop(); // Stop the current level's timeline
		}
	}


	private void updateScene() {
		spawnEnemyUnits();
		spawnItems();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
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

	private void handleCollisions() {
		handleCollisionBetweenLists(friendlyUnits, enemyUnits);
		handleCollisionBetweenLists(userProjectiles, enemyUnits);
		handleCollisionBetweenLists(enemyProjectiles, friendlyUnits);
		handleCollisionBetweenLists(friendlyUnits, items);
		handleEnemyPenetration();
	}

	private void handleCollisionBetweenLists(List<ActiveActorDestructible> actors1,
											 List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					if (otherActor instanceof UserPlane && ((UserPlane) otherActor).isShielded()) {
						System.out.println("Shield absorbed collision");
						actor.destroy(); // Destroy the enemy/projectile
						getRoot().getChildren().remove(actor);
						continue;
					}  else {
						// Otherwise, handle normal collision logic
						actor.takeDamage();
						otherActor.takeDamage();
						}
					actor.takeDamage();
					otherActor.takeDamage();
					if ((actor instanceof Item && otherActor instanceof UserPlane) ||
							(otherActor instanceof Item && actor instanceof UserPlane)) {

						Item item = (actor instanceof Item) ? (Item) actor : (Item) otherActor;
						UserPlane userPlane = (actor instanceof UserPlane) ? (UserPlane) actor : (UserPlane) otherActor;

						// Trigger the item's effect on the user plane
						item.triggerEffect(userPlane);

						// Destroy the item after triggering its effect
						item.destroy();
						root.getChildren().remove(item);
					}
				}
			}
		}
	}
	private void handleEnemyPenetration() {
		enemyUnits.removeIf(enemy -> {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeTrueDamage();
				currentNumberOfEnemies--;
				enemy.destroy();
				root.getChildren().remove(enemy);
				return true; // Remove enemy from the list
			}
			return false;
		});
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
		int currentKills = user.getNumberOfKills();
		user.incrementKillCount(currentNumberOfEnemies - enemyUnits.size());
		levelView.updateKillCount(currentKills); // Update the UI via LevelView
		levelView.killCountText.setVisible(true);
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		gameActive = false;
	}

	protected void loseGame() {
		timeline.stop();
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

}
