package com.example.demo.actors;

import com.example.demo.Config;

/**
 * Class representing a ChargePlane, a type of FighterPlane that moves horizontally.
 */
public class ChargePlane extends FighterPlane {

    private static final String IMAGE_NAME = Config.CHARGE_PLANE_IMAGE;
    private static final int IMAGE_HEIGHT = Config.CHARGE_PLANE_HEIGHT;
    private static final double CHARGE_HORIZONTAL_VELOCITY = Config.CHARGE_HORIZONTAL_VELOCITY;
    private static final int INITIAL_HEALTH = Config.CHARGE_INITIAL_HEALTH;

    /**
     * Constructor for ChargePlane.
     * Initializes the charge plane with its image, position, and health.
     *
     * @param initialXPos the initial X position of the charge plane
     * @param initialYPos the initial Y position of the charge plane
     */
    public ChargePlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the charge plane by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(CHARGE_HORIZONTAL_VELOCITY);
    }

    /**
     * Fires a projectile. Currently, this method returns null as the charge plane does not fire projectiles.
     *
     * @return null as the charge plane does not fire projectiles
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        return null;
    }

    /**
     * Updates the state of the charge plane by updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}