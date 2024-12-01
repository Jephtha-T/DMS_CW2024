package com.example.demo.imagedisplay;

import com.example.demo.actors.ActiveActor;
import com.example.demo.Config;

public class ShieldImage extends ActiveActor {

	private static final String IMAGE_NAME = Config.SHIELD_EFFECT;

	public ShieldImage(double xPosition, double yPosition, int size) {
		super(IMAGE_NAME, size, xPosition, yPosition);
		this.setVisible(false);
		this.setFitHeight(size);
		this.setFitWidth(size);
	}

	public void activateShield() {
		this.setVisible(true);
	}

	public void deactivateShield() {
		this.setVisible(false);
	}

	@Override
	public void updatePosition() {
		// You can add any movement or position updates here if needed
	}
}