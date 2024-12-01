package com.example.demo.actors;

import com.example.demo.Config;

public class ShieldItem extends Item {
    private static final String ITEM_NAME = Config.SHIELD_IMAGE;
    private static final int IMAGE_HEIGHT = Config.SHIELD_SIZE;
    private static final int HORIZONTAL_VELOCITY = Config.SHIELD_ITEM_HORIZONTAL_VELOCITY;

    public ShieldItem(double xPosition, double yPosition) {
        super(ITEM_NAME, IMAGE_HEIGHT, xPosition, yPosition);
        this.setVisible(true);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public void triggerEffect(UserPlane userPlane) {
        userPlane.activateShield();
    }
}
