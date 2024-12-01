package com.example.demo.imagedisplay;

import com.example.demo.actors.ActiveActor;
import com.example.demo.Config;

/**
 * Class representing the Shield image displayed in the game.
 * Extends the ActiveActor class.
 */
public class ShieldImage extends ActiveActor {

	private static final String IMAGE_NAME = Config.SHIELD_EFFECT;

	/**
	 * Constructor for ShieldImage.
	 * Initializes the Shield image with its position and size.
	 *
	 * @param xPosition the X position of the Shield image
	 * @param yPosition the Y position of the Shield image
	 * @param size the size of the Shield image
	 */
	public ShieldImage(double xPosition, double yPosition, int size) {
		super(IMAGE_NAME, size, xPosition, yPosition);
		this.setVisible(false);
		this.setFitHeight(size);
		this.setFitWidth(size);
	}

	/**
	 * Activates the shield by making the image visible.
	 */
	public void activateShield() {
		this.setVisible(true);
	}

	/**
	 * Deactivates the shield by making the image invisible.
	 */
	public void deactivateShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the Shield image.
	 * Override this method to add any movement or position updates if needed.
	 */
	@Override
	public void updatePosition() {
		// You can add any movement or position updates here if needed
	}
}