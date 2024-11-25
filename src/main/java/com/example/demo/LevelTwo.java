package com.example.demo;



public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_TWO_BACKGROUND;
	private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;
	private static final int TOTAL_ITEMS = Config.TOTAL_ITEMS;
	private static final double ITEM_SPAWN_PROBABILITY = Config.ITEM_SPAWN_PROBABILITY;
	private final Boss boss;

    public LevelTwo(double screenHeight, double screenWidth) {
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
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

}
