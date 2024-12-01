package com.example.demo.actors;

public abstract class Item extends ActiveActorDestructible {

    private final int horizontalVelocity;

    protected Item(String imageName, int imageHeight, double xPosition, double yPosition, int horizontalVelocity) {
        super(imageName, imageHeight, xPosition, yPosition);
        this.horizontalVelocity = horizontalVelocity;
        this.setVisible(true);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    // Abstract method to define the effect in subclasses
    public abstract void triggerEffect(UserPlane userPlane);
}
