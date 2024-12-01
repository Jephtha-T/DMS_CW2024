package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a BossProjectile, a type of Projectile that moves horizontally.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = Config.BOSS_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.BOSS_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.BOSS_PROJECTILE_HORIZONTAL_VELOCITY;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructor for BossProjectile.
	 * Initializes the boss projectile with its image, position, and size.
	 *
	 * @param initialYPos the initial Y position of the boss projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the boss projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the boss projectile by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}