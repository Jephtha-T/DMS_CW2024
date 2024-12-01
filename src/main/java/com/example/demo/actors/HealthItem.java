package com.example.demo.actors;

import com.example.demo.Config;

public class HealthItem extends Item {
    public HealthItem(double xPosition, double yPosition) {
        super(Config.HEALTH_IMAGE, Config.HEALTH_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    @Override
    public void triggerEffect(UserPlane mUserPlane) {
        // Restore one heart to the user plane
        mUserPlane.restoreHealth(1);
    }
}
