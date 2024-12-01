package com.example.demo.imagedisplay;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * Class representing the heart display in the game.
 * Manages the display of heart images to represent health.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = Config.HEART_IMAGE;
	private static final int HEART_HEIGHT = Config.HEART_HEIGHT;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructor for HeartDisplay.
	 * Initializes the heart display with its position and number of hearts.
	 *
	 * @param xPosition the X position of the heart display container
	 * @param yPosition the Y position of the heart display container
	 * @param heartsToDisplay the number of hearts to display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the heart display.
	 * Sets the layout position of the container.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the hearts in the display.
	 * Adds the specified number of heart images to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes a heart from the display.
	 * Removes the first heart image from the container if it is not empty.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Adds a heart to the display.
	 * Adds a new heart image to the container.
	 */
	public void addHeart() {
		ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Gets the container for the heart display.
	 *
	 * @return the HBox container for the heart display
	 */
	public HBox getContainer() {
		return container;
	}
}