package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.*;
import java.util.logging.Logger;
import java.util.Objects;

/**
 * Abstract class representing an active actor in the game.
 * Extends ImageView to display an image for the actor.
 */
public abstract class ActiveActor extends ImageView {

	private static final Logger LOGGER = Logger.getLogger(ActiveActor.class.getName());
	private static final String IMAGE_LOCATION = Config.IMAGE_PATH_PREFIX;

	/**
	 * Constructor for ActiveActor.
	 * Initializes the actor's image, position, and size.
	 *
	 * @param imageName   the name of the image file
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the actor
	 * @param initialYPos the initial Y position of the actor
	 */
	protected ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		try {
			this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		} catch (NullPointerException e) {
			LOGGER.severe("Image resource not found: " + IMAGE_LOCATION + imageName);
			throw new IllegalArgumentException("Failed to load image: " + IMAGE_LOCATION + imageName, e);
		}
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method to update the position of the actor.
	 * Must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 *
	 * @param horizontalMove the amount to move horizontally
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 *
	 * @param verticalMove the amount to move vertically
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}