package com.example.demo.imagedisplay;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class representing the Win image displayed in the game.
 * Extends the ImageView class from JavaFX.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = Config.WIN_IMAGE;
	private static final int HEIGHT = Config.WIN_IMAGE_HEIGHT;
	private static final int WIDTH = Config.WIN_IMAGE_WIDTH;

	/**
	 * Constructor for WinImage.
	 * Initializes the Win image with its position and size.
	 *
	 * @param xPosition the X position of the Win image
	 * @param yPosition the Y position of the Win image
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Shows the Win image by making it visible.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}