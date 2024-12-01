package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a UserProjectile, a type of Projectile fired by the user plane.
 * The UserProjectile moves horizontally with a specified velocity.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = Config.USER_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.USER_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.USER_PROJECTILE_HORIZONTAL_VELOCITY;

	/**
	 * Constructor for UserProjectile.
	 * Initializes the user projectile with its image, size, and initial position.
	 *
	 * @param initialXPos the initial X position of the user projectile
	 * @param initialYPos the initial Y position of the user projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the user projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the user projectile by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}