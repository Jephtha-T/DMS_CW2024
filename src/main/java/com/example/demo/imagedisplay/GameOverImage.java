package com.example.demo.imagedisplay;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class representing the Game Over image displayed in the game.
 * Extends the ImageView class from JavaFX.
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = Config.GAME_OVER_IMAGE;

	/**
	 * Constructor for GameOverImage.
	 * Initializes the Game Over image with its position on the screen.
	 *
	 * @param xPosition the X position of the Game Over image
	 * @param yPosition the Y position of the Game Over image
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}