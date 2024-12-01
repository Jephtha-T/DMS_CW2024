package com.example.demo.actors;

import com.example.demo.Config;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = Config.ENEMY_PLANE_IMAGE ;
	private static final int IMAGE_HEIGHT = Config.ENEMY_PLANE_HEIGHT;
	private static final double HORIZONTAL_VELOCITY = Config.ENEMY_HORIZONTAL_VELOCITY;
	private static final double PROJECTILE_X_POSITION_OFFSET = Config.ENEMY_PROJECTILE_X_OFFSET;
	private static final double PROJECTILE_Y_POSITION_OFFSET = Config.ENEMY_PROJECTILE_Y_OFFSET;
	private static final int INITIAL_HEALTH = Config.ENEMY_INITIAL_HEALTH;
	private static final double FIRE_RATE = Config.ENEMY_FIRE_RATE;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (new java.security.SecureRandom().nextDouble() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
