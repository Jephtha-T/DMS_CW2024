package com.example.demo;

public abstract class Item extends ActiveActorDestructible {

    public Item(String imageName, int imageHeight, double xPosition, double yPosition) {
        super(imageName, imageHeight, xPosition, yPosition);
    }

    @Override
    public abstract void updatePosition();

    // Abstract method to define the effect in subclasses
    protected abstract void triggerEffect(UserPlane mUserPlane);
}
