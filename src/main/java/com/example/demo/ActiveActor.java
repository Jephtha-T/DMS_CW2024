package com.example.demo;

import javafx.scene.image.*;

import java.util.Objects;

public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_LOCATION = Config.IMAGE_PATH_PREFIX;

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
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
