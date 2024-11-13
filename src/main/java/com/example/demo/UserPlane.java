package com.example.demo;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 75;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private boolean isShielded;
	private int numberOfKills;
	private int framesWithShieldActivated;
	private final ShieldImage shieldImage;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
	}
	
	@Override
	public void updatePosition() {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			shieldImage.setLayoutY(newPosition);
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
		System.out.println("shield on");
		shieldImage.activateShield();  // Activates the shield image when the shield item is collected

	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		System.out.println("shield off");
		shieldImage.deactivateShield();
	}

	public ShieldImage getShieldImage() {
		return shieldImage;
	}
}
