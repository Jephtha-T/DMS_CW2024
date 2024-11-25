package com.example.demo;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = Config.USER_PLANE_IMAGE;
	private static final double Y_UPPER_BOUND = Config.Y_UPPER_BOUND;
	private static final double Y_LOWER_BOUND = Config.Y_LOWER_BOUND;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = Config.USERPLANE_HEIGHT;
	private static final int VERTICAL_VELOCITY = Config.USERPLANE_VERTICAL_VELOCITY;
	private static final double PROJECTILE_X_POSITION = Config.USER_PROJECTILE_X_OFFSET;
	private static final double PROJECTILE_Y_POSITION_OFFSET = Config.USER_PROJECTILE_Y_OFFSET;
	private static final int MAX_FRAMES_WITH_SHIELD = Config.MAX_FRAMES_WITH_SHIELD;
	private double velocityMultiplier;
	private boolean isShielded;
	private int numberOfKills;
	private int framesWithShieldActivated;
	public static final ShieldImage shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
		double newPosition = getLayoutY() + getTranslateY();
		shieldImage.setTranslateX(getTranslateX() - 20); // Adjust X if necessary
		shieldImage.setTranslateY(getTranslateY() - 20); // Adjust Y if necessary
		if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
			this.setTranslateY(initialTranslateY);
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private void updateShield() {

		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		}
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount(int count) {
		this.numberOfKills+= count;
	}

	protected void activateShield() {
		isShielded = true;
		shieldImage.activateShield();  // Activate the shield image
		shieldImage.setTranslateX(getTranslateX() - 20); // Adjust position relative to the plane
		shieldImage.setTranslateY(getTranslateY() - 20);
		shieldImage.setVisible(true);
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.deactivateShield();  // Deactivate the shield image
		shieldImage.setVisible(false);
	}

	@Override
	public void takeDamage() {
		if (isShielded) {
			return; // Prevent health reduction
		}
		super.takeDamage(); // Otherwise, apply damage normally
	}

	public void takeTrueDamage() {
		super.takeDamage(); // Otherwise, apply damage normally
	}

	public boolean isShielded() {
		return isShielded;
	}
}
