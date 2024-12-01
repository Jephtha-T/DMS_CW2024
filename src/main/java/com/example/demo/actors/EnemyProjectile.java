package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing an EnemyProjectile, a type of Projectile that moves horizontally.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = Config.ENEMY_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.ENEMY_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY;

	/**
	 * Constructor for EnemyProjectile.
	 * Initializes the enemy projectile with its image, position, and size.
	 *
	 * @param initialXPos the initial X position of the enemy projectile
	 * @param initialYPos the initial Y position of the enemy projectile
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the enemy projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the enemy projectile by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}