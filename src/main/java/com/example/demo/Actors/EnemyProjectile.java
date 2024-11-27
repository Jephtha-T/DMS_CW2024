package com.example.demo.Actors;

import com.example.demo.Config;

public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = Config.ENEMY_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.ENEMY_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.ENEMY_PROJECTILE_HORIZONTAL_VELOCITY;


	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}


}
