package com.example.demo.Actors;

import com.example.demo.Config;

public class HealthItem extends Item {
    private static final String ITEM_NAME = Config.HEALTH_IMAGE;
    private static final int IMAGE_HEIGHT = Config.HEALTH_SIZE;
    private static final int HORIZONTAL_VELOCITY = Config.SHIELD_ITEM_HORIZONTAL_VELOCITY;

    public HealthItem(double xPosition, double yPosition) {
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
    public void triggerEffect(UserPlane mUserPlane) {
        // Restore one heart to the user plane
        mUserPlane.restoreHealth(1);
    }
}
