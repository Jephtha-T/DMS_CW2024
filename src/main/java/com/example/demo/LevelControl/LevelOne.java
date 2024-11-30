package com.example.demo.LevelControl;

import com.example.demo.Actors.*;
import com.example.demo.Config;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_ONE_BACKGROUND;
	private static final String NEXT_LEVEL = "com.example.demo.LevelControl.LevelTwo";
	private static final int TOTAL_ENEMIES = Config.TOTAL_ENEMIES;
	private static final int TOTAL_ITEMS = Config.TOTAL_ITEMS;
	private static final int KILLS_TO_ADVANCE = Config.KILLS_TO_ADVANCE_LEVELONE;
	private static final double ENEMY_SPAWN_PROBABILITY = Config.ENEMY_SPAWN_PROBABILITY;
	private static final double ITEM_SPAWN_PROBABILITY = Config.ITEM_SPAWN_PROBABILITY;
	private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;


	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget() && !isTransitionInProgress()) {
			startLevelTransition(); // Mark the transition as started
			notifyObservers(NEXT_LEVEL); // Notify LevelManager of the next level
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double randomValue = Math.random(); // Random value between 0 and 1

				ActiveActorDestructible newEnemy;
				if (randomValue < 0.7) { // 70% chance for normal EnemyPlane
					double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
					newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				} else{ // 30% chance for BomberPlane
					newEnemy = BomberPlane.createInRightHalf(getScreenWidth(), getScreenHeight());
				}
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected void spawnItems() {
		int currentNumberOfItems = getCurrentNumberOfItems();
		for (int i = 0; i < TOTAL_ITEMS - currentNumberOfItems; i++) {
			if (Math.random() < ITEM_SPAWN_PROBABILITY) {
				double newItemInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ShieldItem newShieldItem = new ShieldItem(getScreenWidth(), newItemInitialYPosition);
				addItem(newShieldItem); // This adds it to the root and keeps track of the items
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, getKillsToAdvance());
	}

	private boolean userHasReachedKillTarget() {

		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected int getKillsToAdvance() {
		return KILLS_TO_ADVANCE;
	}


}
