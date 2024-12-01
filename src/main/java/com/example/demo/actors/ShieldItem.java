package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a ShieldItem, a type of Item that activates a shield for the user plane.
 */
public class ShieldItem extends Item {

    /**
     * Constructor for ShieldItem.
     * Initializes the shield item with its image, size, position, and velocity.
     *
     * @param xPosition the initial X position of the shield item
     * @param yPosition the initial Y position of the shield item
     */
    public ShieldItem(double xPosition, double yPosition) {
        super(Config.SHIELD_IMAGE, Config.SHIELD_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    /**
     * Triggers the effect of the shield item, activating a shield for the user plane.
     *
     * @param userPlane the user plane on which the shield will be activated
     */
    @Override
    public void triggerEffect(UserPlane userPlane) {
        userPlane.activateShield();
    }
}