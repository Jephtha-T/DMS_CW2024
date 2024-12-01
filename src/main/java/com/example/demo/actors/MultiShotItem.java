package com.example.demo.actors;

import com.example.demo.Config;

public class MultiShotItem extends Item {
    private static final String ITEM_NAME = Config.MULTISHOT_IMAGE;
    private static final int IMAGE_HEIGHT = Config.MULTISHOT_SIZE;
    private static final int HORIZONTAL_VELOCITY = Config.SHIELD_ITEM_HORIZONTAL_VELOCITY;

    public MultiShotItem(double xPosition, double yPosition) {
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
        // Enable multi-shot mode in the user plane
        mUserPlane.enableMultiShot();
    }
}
