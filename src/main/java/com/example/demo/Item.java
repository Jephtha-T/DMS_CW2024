package com.example.demo;

public abstract class Item extends ActiveActorDestructible {

    protected Item(String imageName, int imageHeight, double xPosition, double yPosition) {
        super(imageName, imageHeight, xPosition, yPosition);
    }

    @Override
    public abstract void updatePosition();

    @Override
    public void takeDamage() {
        //Item does not take damage
    }
    // Abstract method to define the effect in subclasses
    protected abstract void triggerEffect(UserPlane mUserPlane);
}
