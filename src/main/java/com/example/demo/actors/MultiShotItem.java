package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a MultiShotItem, a type of Item that enables multi-shot capability for the user plane.
 */
public class MultiShotItem extends Item {

    /**
     * Constructor for MultiShotItem.
     * Initializes the multi-shot item with its image, size, position, and velocity.
     *
     * @param xPosition the initial X position of the multi-shot item
     * @param yPosition the initial Y position of the multi-shot item
     */
    public MultiShotItem(double xPosition, double yPosition) {
        super(Config.MULTISHOT_IMAGE, Config.MULTISHOT_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    /**
     * Triggers the effect of the multi-shot item, enabling multi-shot capability for the user plane.
     *
     * @param userPlane the user plane on which the multi-shot capability will be enabled
     */
    @Override
    public void triggerEffect(UserPlane userPlane) {
        userPlane.enableMultiShot();
    }
}