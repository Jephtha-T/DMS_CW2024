package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.*;
import java.util.logging.Logger;
import java.util.Objects;

public abstract class ActiveActor extends ImageView {

	private static final Logger LOGGER = Logger.getLogger(ActiveActor.class.getName());
	private static final String IMAGE_LOCATION = Config.IMAGE_PATH_PREFIX;


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

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
