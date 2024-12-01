package com.example.demo.actors;

import com.example.demo.Config;

public class BomberPlane extends FighterPlane {

    private static final String IMAGE_NAME = Config.BOMBER_PLANE_IMAGE;
    private static final int IMAGE_HEIGHT = Config.BOMBER_PLANE_HEIGHT;
    private static final double VERTICAL_VELOCITY = Config.BOMBER_VERTICAL_VELOCITY; // Adjusted for vertical movement
    private static final double PROJECTILE_X_POSITION_OFFSET = Config.ENEMY_PROJECTILE_X_OFFSET;
    private static final double PROJECTILE_Y_POSITION_OFFSET = Config.ENEMY_PROJECTILE_Y_OFFSET;
    private static final int INITIAL_HEALTH = Config.BOMBER_INITIAL_HEALTH;
    private static final double FIRE_RATE = Config.ENEMY_FIRE_RATE;

    public BomberPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        // Move vertically downwards
        moveVertically(VERTICAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (new java.security.SecureRandom().nextDouble() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new BombProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    // Utility method to initialize a BomberPlane at a random position in the right half of the screen
    public static BomberPlane createInRightHalf(double screenWidth) {
        double initialXPos = screenWidth / 2 + Math.random() * (screenWidth / 2); // Random position in right half
        double initialYPos = 0; // Start at the top
        return new BomberPlane(initialXPos, initialYPos);
    }
}
