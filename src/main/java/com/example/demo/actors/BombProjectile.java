package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a BombProjectile, a type of Projectile that moves horizontally.
 */
public class BombProjectile extends Projectile {

    private static final String IMAGE_NAME = Config.BOMB_PROJECTILE_IMAGE;
    private static final int IMAGE_HEIGHT = Config.BOMB_PROJECTILE_HEIGHT;
    private static final int HORIZONTAL_VELOCITY = Config.BOMB_PROJECTILE_HORIZONTAL_VELOCITY;

    /**
     * Constructor for BombProjectile.
     * Initializes the bomb projectile with its image, position, and size.
     *
     * @param initialXPos the initial X position of the bomb projectile
     * @param initialYPos the initial Y position of the bomb projectile
     */
    public BombProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the bomb projectile by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the bomb projectile by updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}