package com.example.demo.actors;

/**
 * Abstract class representing a FighterPlane, which is a type of ActiveActorDestructible.
 * FighterPlane has health and can fire projectiles.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	protected int health;

	/**
	 * Constructor for FighterPlane.
	 * Initializes the fighter plane with its image, position, and health.
	 *
	 * @param imageName the name of the image representing the fighter plane
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the fighter plane
	 * @param initialYPos the initial Y position of the fighter plane
	 * @param health the initial health of the fighter plane
	 */
	protected FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to fire a projectile.
	 *
	 * @return an instance of ActiveActorDestructible representing the fired projectile
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Handles damage taken by the fighter plane. Decreases health by one and destroys the plane if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (health == 0) {
			this.destroy();
		}
	}

	/**
	 * Gets the X position for the projectile fired by the fighter plane.
	 *
	 * @param xPositionOffset the offset to be added to the X position
	 * @return the calculated X position for the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Gets the Y position for the projectile fired by the fighter plane.
	 *
	 * @param yPositionOffset the offset to be added to the Y position
	 * @return the calculated Y position for the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return the current health
	 */
	public int getHealth() {
		return health;
	}
}