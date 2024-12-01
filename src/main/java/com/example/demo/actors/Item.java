package com.example.demo.actors;

public abstract class Item extends ActiveActorDestructible {

    protected Item(String imageName, int imageHeight, double xPosition, double yPosition) {
        super(imageName, imageHeight, xPosition, yPosition);
    }

    @Override
    public abstract void updatePosition();

    // Abstract method to define the effect in subclasses
    public abstract void triggerEffect(UserPlane mUserPlane);
}
