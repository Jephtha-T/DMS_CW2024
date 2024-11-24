package com.example.demo;

public class ShieldImage extends ActiveActor {

	private static final String IMAGE_NAME = Config.SHIELD_IMAGE;
	private static final int SHIELD_SIZE = Config.SHIELD_SIZE;
	private boolean isActive;

	public ShieldImage(double xPosition, double yPosition) {
		super(IMAGE_NAME, SHIELD_SIZE, xPosition, yPosition);
		this.setVisible(false);
		this.isActive = false;
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void activateShield() {
		this.setVisible(true);
		this.isActive = true;
	}

	public void deactivateShield() {
		this.setVisible(false);
		this.isActive = false;
	}

	@Override
	public void updatePosition() {
		// You can add any movement or position updates here if needed
	}
}