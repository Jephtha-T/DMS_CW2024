package com.example.demo;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = Config.USER_PROJECTILE_IMAGE;
	private static final int IMAGE_HEIGHT = Config.USER_PROJECTILE_HEIGHT;
	private static final int HORIZONTAL_VELOCITY = Config.USER_PROJECTILE_HORIZONTAL_VELOCITY;


	public UserProjectile(double initialXPos, double initialYPos) {
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
