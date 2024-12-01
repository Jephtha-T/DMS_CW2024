package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a HealthItem, a type of Item that restores health to the user plane.
 */
public class HealthItem extends Item {

    /**
     * Constructor for HealthItem.
     * Initializes the health item with its image, size, position, and velocity.
     *
     * @param xPosition the initial X position of the health item
     * @param yPosition the initial Y position of the health item
     */
    public HealthItem(double xPosition, double yPosition) {
        super(Config.HEALTH_IMAGE, Config.HEALTH_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    /**
     * Triggers the effect of the health item, restoring one heart to the user plane.
     *
     * @param mUserPlane the user plane to which the health will be restored
     */
    @Override
    public void triggerEffect(UserPlane mUserPlane) {
        // Restore one heart to the user plane
        mUserPlane.restoreHealth(1);
    }
}