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

    public SpriteSheetAnimation(String spritesheetPath, int frameWidth, int frameHeight, int totalFrames, double frameDuration) {
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