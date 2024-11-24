package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL_TWO_BACKGROUND;
	private static final int PLAYER_INITIAL_HEALTH = Config.USER_INITIAL_HEALTH;
	private final Boss boss;

    public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
		System.out.println("LevelTwo initialized with boss and player.");
	}

	@Override
	protected void initializeFriendlyUnits() {
		System.out.println("Initializing friendly units for LevelTwo...");
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

	}

	@Override
	protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

}
