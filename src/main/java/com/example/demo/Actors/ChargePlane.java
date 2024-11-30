package com.example.demo.Actors;

import com.example.demo.Config;

public class ChargePlane extends FighterPlane {

    private static final String IMAGE_NAME = Config.CHARGE_PLANE_IMAGE ;
    private static final int IMAGE_HEIGHT = Config.CHARGE_PLANE_HEIGHT;
    private static final double CHARGE_HORIZONTAL_VELOCITY = Config.CHARGE_HORIZONTAL_VELOCITY;
    private static final int INITIAL_HEALTH = Config.CHARGE_INITIAL_HEALTH;


    public ChargePlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(CHARGE_HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
