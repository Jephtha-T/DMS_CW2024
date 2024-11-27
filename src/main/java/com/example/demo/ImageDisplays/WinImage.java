package com.example.demo.ImageDisplays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class WinImage extends ImageView {

	private static final String IMAGE_NAME = Config.WIN_IMAGE;
	private static final int HEIGHT = Config.WIN_IMAGE_HEIGHT;
	private static final int WIDTH = Config.WIN_IMAGE_WIDTH;

	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
	
	public void showWinImage() {
		this.setVisible(true);
	}

}
