package com.example.demo.actors;

import com.example.demo.Config;

import java.util.*;

/**
 * Class representing a Boss, a type of FighterPlane with special abilities like shielding and a unique move pattern.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = Config.BOSS_IMAGE_NAME;
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = Config.PROJECTILE_Y_POSITION_OFFSET;
	private static final double BOSS_FIRE_RATE = Config.BOSS_FIRE_RATE;
	private static final double BOSS_SHIELD_PROBABILITY = Config.BOSS_SHIELD_PROBABILITY;
	private static final int IMAGE_HEIGHT = Config.BOSS_IMAGE_HEIGHT;
	private static final int VERTICAL_VELOCITY = Config.BOSS_VERTICAL_VELOCITY;
	private static final int BOSS_HEALTH = Config.BOSS_INITIAL_HEALTH;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = Config.BOSS_MAX_FRAMES_WITH_SHIELD;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private final ShieldEffect shieldEffect;

	/**
	 * Constructor for Boss.
	 * Initializes the boss with its image, position, health, and move pattern.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, BOSS_HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
		shieldEffect = new ShieldEffect(INITIAL_X_POSITION, INITIAL_Y_POSITION, IMAGE_HEIGHT);
	}

	/**
	 * Updates the position of the boss by moving it vertically according to the move pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();

		shieldEffect.setLayoutY(currentPosition);

		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the boss by updating its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss meets the fire rate condition.
	 *
	 * @return a new BossProjectile if the fire rate condition is met, otherwise null
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles damage taken by the boss. Damage is only taken if the shield is not active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Resets the state of the boss.
	 * This method resets the boss's health to its initial value and deactivates the shield.
	 */
	public void resetState() {
		this.health = Config.BOSS_INITIAL_HEALTH; // Reset health to initial value
		deactivateShield();                       // Reset shield state
	}



	/**
	 * Gets the current health of the boss.
	 *
	 * @return the current health of the boss
	 */
	public int getBossHealth() {
		return getHealth();
	}

	/**
	 * Initializes the move pattern for the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss. Activates or deactivates the shield based on probability and duration.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		} else if (new java.security.SecureRandom().nextDouble() < BOSS_SHIELD_PROBABILITY) {
			activateShield();
		}
	}

	/**
	 * Gets the next move in the move pattern.
	 *
	 * @return the next move value
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss fires a projectile in the current frame based on the fire rate.
	 *
	 * @return true if the boss fires in the current frame, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return new java.security.SecureRandom().nextDouble() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial position for the projectile fired by the boss.
	 *
	 * @return the initial Y position for the projectile
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Activates the shield for the boss.
	 */
    protected void activateShield() {
		isShielded = true;
		shieldEffect.activateShield();
	}

	/**
	 * Deactivates the shield for the boss.
	 */
    protected void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldEffect.deactivateShield();
	}

	/**
	 * Gets the shield image associated with the boss.
	 *
	 * @return the shield image
	 */
	public ShieldEffect getShieldImage() {
		return shieldEffect;
	}
}