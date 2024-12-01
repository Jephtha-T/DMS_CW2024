package com.example.demo.actors;

import com.example.demo.Config;

public class BombProjectile extends Projectile {

    private static final String IMAGE_NAME = Config.BOMB_PROJECTILE_IMAGE;
    private static final int IMAGE_HEIGHT = Config.BOMB_PROJECTILE_HEIGHT;
    private static final int HORIZONTAL_VELOCITY = Config.BOMB_PROJECTILE_HORIZONTAL_VELOCITY;


    public BombProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }


}
