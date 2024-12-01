package com.example.demo.levels;

import com.example.demo.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Handles sprite sheet animations for game elements.
 * Preloads the sprite sheet and manages the animation frames.
 */
public class SpriteSheetAnimation {
    private static final Image SPRITE_SHEET_CACHE;

    static {
        // Preload the sprite sheet image
        SPRITE_SHEET_CACHE = new Image(Objects.requireNonNull(SpriteSheetAnimation.class.getResourceAsStream(Config.EXPLOSION_SPRITESHEET_PATH)));
    }

    private final ImageView imageView;
    private final int frameWidth;
    private final int frameHeight;
    private final int columns;
    private final Timeline timeline;

    /**
     * Constructor for SpriteSheetAnimation.
     * Initializes the animation with the specified parameters.
     *
     * @param frameWidth the width of each frame in the sprite sheet
     * @param frameHeight the height of each frame in the sprite sheet
     * @param totalFrames the total number of frames in the animation
     * @param frameDuration the duration of each frame in seconds
     */
    public SpriteSheetAnimation(int frameWidth, int frameHeight, int totalFrames, double frameDuration) {
        this.imageView = new ImageView(SPRITE_SHEET_CACHE);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.columns = (int) (SPRITE_SHEET_CACHE.getWidth() / frameWidth);

        this.imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(frameDuration), e -> updateFrame())
        );
        this.timeline.setCycleCount(totalFrames); // Play only once
    }

    private int currentFrame = 0;

    /**
     * Updates the current frame of the animation.
     * Calculates the x and y coordinates of the frame in the sprite sheet.
     */
    private void updateFrame() {
        int x = (currentFrame % columns) * frameWidth;
        int y = (currentFrame / columns) * frameHeight;
        imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameHeight));
        currentFrame++;
    }

    /**
     * Plays the animation once and removes the image view from the root group after the animation finishes.
     *
     * @param root the root group to which the image view is added
     */
    public void playOnceAndRemoveAfter(Group root) {
        // Check if the ImageView is already in the root before adding
        if (!root.getChildren().contains(imageView)) {
            root.getChildren().add(imageView);
        }
        timeline.setOnFinished(e -> stopAndRemove(root)); // Remove after the animation finishes
        timeline.play();
    }

    /**
     * Stops the animation and removes the image view from the root group.
     *
     * @param root the root group from which the image view is removed
     */
    public void stopAndRemove(Group root) {
        timeline.stop();
        root.getChildren().remove(imageView);
    }

    /**
     * Gets the image view used for the animation.
     *
     * @return the image view
     */
    public ImageView getImageView() {
        return imageView;
    }
}