package com.example.demo.actors;

/**
 * Abstract class representing a Projectile, which is a type of ActiveActorDestructible.
 * Projectiles have an image, size, and initial position, and can take damage and update their position.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructor for Projectile.
	 * Initializes the projectile with its image, size, and initial position.
	 *
	 * @param imageName the name of the image representing the projectile
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the projectile
	 * @param initialYPos the initial Y position of the projectile
	 */
	protected Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles damage taken by the projectile. Destroys the projectile.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the position of the projectile.
	 */
	@Override
	public abstract void updatePosition();
}