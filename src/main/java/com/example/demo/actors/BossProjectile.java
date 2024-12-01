package com.example.demo.actors;

import com.example.demo.Config;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = Config.BOSS_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.BOSS_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.BOSS_PROJECTILE_HORIZONTAL_VELOCITY;
	private static final int INITIAL_X_POSITION = 950;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
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
