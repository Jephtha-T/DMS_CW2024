package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ActiveActor {

	private static final String IMAGE_NAME = "shield.png";
	private static final int SHIELD_SIZE = 200;
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

	// Method to determine if damage should be nullified
	public boolean isDamageNullified() {
		return isActive;
	}

	@Override
	public void updatePosition() {
		// You can add any movement or position updates here if needed
	}
}