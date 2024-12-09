package com.example.demo.imagedisplay;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class representing the Game Over image displayed in the game.
 * Extends the ImageView class from JavaFX.
 */
public class HelpImage extends ImageView {

    private static final String IMAGE_NAME = Config.HELP_IMAGE;
    private static final int HEIGHT = Config.HELP_IMAGE_HEIGHT;
    private static final int WIDTH = Config.HELP_IMAGE_WIDTH;

    /**
     * Constructor for GameOverImage.
     * Initializes the Game Over image with its position on the screen.
     *
     * @param xPosition the X position of the Win image
     * @param yPosition the Y position of the Win image
     */
    public HelpImage(double xPosition, double yPosition) {
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Shows the Help image by making it visible.
     */
    public void showHelpImage() {
        this.setVisible(true);
    }
}