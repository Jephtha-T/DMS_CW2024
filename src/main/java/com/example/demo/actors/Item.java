package com.example.demo.actors;

/**
 * Abstract class representing an Item, which is a type of ActiveActorDestructible.
 * Items have a horizontal velocity and can trigger effects on the user plane.
 */
public abstract class Item extends ActiveActorDestructible {

    private final int horizontalVelocity;

    /**
     * Constructor for Item.
     * Initializes the item with its image, size, position, and horizontal velocity.
     *
     * @param imageName the name of the image representing the item
     * @param imageHeight the height of the image
     * @param xPosition the initial X position of the item
     * @param yPosition the initial Y position of the item
     * @param horizontalVelocity the horizontal velocity of the item
     */
    protected Item(String imageName, int imageHeight, double xPosition, double yPosition, int horizontalVelocity) {
        super(imageName, imageHeight, xPosition, yPosition);
        this.horizontalVelocity = horizontalVelocity;
        this.setVisible(true);
    }

    /**
     * Updates the position of the item by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    /**
     * Updates the state of the item by updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Handles damage taken by the item. This method is empty as items do not take damage.
     */
    @Override
    public void takeDamage() {
    }

    /**
     * Abstract method to trigger the effect of the item on the user plane.
     *
     * @param userPlane the user plane on which the effect will be triggered
     */
    public abstract void triggerEffect(UserPlane userPlane);
}