package com.example.demo.actors;

import com.example.demo.Config;

public class ShieldItem extends Item {
    public ShieldItem(double xPosition, double yPosition) {
        super(Config.SHIELD_IMAGE, Config.SHIELD_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    @Override
    public void triggerEffect(UserPlane userPlane) {
        userPlane.activateShield();
    }
}
