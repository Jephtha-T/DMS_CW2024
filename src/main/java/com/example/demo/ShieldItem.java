package com.example.demo;

public class ShieldItem extends Item {
    private static final String SHIELD_ITEM_NAME = "shield.png"; // Image for ShieldItem
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -10;

    public ShieldItem(double xPosition, double yPosition) {
        super(SHIELD_ITEM_NAME, IMAGE_HEIGHT, xPosition, yPosition);
        this.setVisible(true);
    }

    @Override
    protected void triggerEffect(UserPlane userPlane) {
        userPlane.activateShield();
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
    public void takeDamage() {

    }
}
