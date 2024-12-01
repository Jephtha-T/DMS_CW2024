package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing an EnemyPlane, a type of FighterPlane that moves horizontally and can fire projectiles.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = Config.ENEMY_PLANE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.ENEMY_PLANE_HEIGHT;
	private static final double HORIZONTAL_VELOCITY = Config.ENEMY_HORIZONTAL_VELOCITY;
	private static final double PROJECTILE_X_POSITION_OFFSET = Config.ENEMY_PROJECTILE_X_OFFSET;
	private static final double PROJECTILE_Y_POSITION_OFFSET = Config.ENEMY_PROJECTILE_Y_OFFSET;
	private static final int INITIAL_HEALTH = Config.ENEMY_INITIAL_HEALTH;
	private static final double FIRE_RATE = Config.ENEMY_FIRE_RATE;

	/**
	 * Constructor for EnemyPlane.
	 * Initializes the enemy plane with its image, position, and health.
	 *
	 * @param initialXPos the initial X position of the enemy plane
	 * @param initialYPos the initial Y position of the enemy plane
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile if the enemy plane meets the fire rate condition.
	 *
	 * @return a new EnemyProjectile if the fire rate condition is met, otherwise null
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (new java.security.SecureRandom().nextDouble() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}