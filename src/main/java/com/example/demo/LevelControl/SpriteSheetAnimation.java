package com.example.demo.LevelControl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class SpriteSheetAnimation {
    private static Image spriteSheetCache; // Cache for the sprite sheet
    private final ImageView imageView;
    private final int frameWidth;
    private final int frameHeight;
    private final int totalFrames;
    private final int columns;
    private final Timeline timeline;

    public SpriteSheetAnimation(String spritesheetPath, int frameWidth, int frameHeight, int totalFrames, double frameDuration) {
        if (spriteSheetCache == null) {
            spriteSheetCache = new Image(Objects.requireNonNull(getClass().getResourceAsStream(spritesheetPath)));
        }
        this.imageView = new ImageView(spriteSheetCache);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.columns = (int) (spriteSheetCache.getWidth() / frameWidth);

        this.imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(frameDuration), e -> updateFrame())
        );
        this.timeline.setCycleCount(totalFrames); // Play only once
    }

    private int currentFrame = 0;

    private void updateFrame() {
        int x = (currentFrame % columns) * frameWidth;
        int y = (currentFrame / columns) * frameHeight;
        imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameHeight));
        currentFrame++;
    }


    public void playOnceAndRemoveAfter(Group root) {
        // Check if the ImageView is already in the root before adding
        if (!root.getChildren().contains(imageView)) {
            root.getChildren().add(imageView);
        }
        timeline.setOnFinished(e -> stopAndRemove(root)); // Remove after the animation finishes
        timeline.play();
    }


    public void stopAndRemove(Group root) {
        timeline.stop();
        root.getChildren().remove(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }
}

