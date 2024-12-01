package com.example.demo.actors;

import com.example.demo.Config;

public class MultiShotItem extends Item {
    public MultiShotItem(double xPosition, double yPosition) {
        super(Config.MULTISHOT_IMAGE, Config.MULTISHOT_SIZE, xPosition, yPosition, Config.ITEM_HORIZONTAL_VELOCITY);
    }

    @Override
    public void triggerEffect(UserPlane userPlane) {
        userPlane.enableMultiShot();
    }
}
