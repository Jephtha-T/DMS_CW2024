package com.example.demo.ImageDisplays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = Config.GAME_OVER_IMAGE;

	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()) );
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}