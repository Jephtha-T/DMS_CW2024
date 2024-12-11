package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.imagedisplay.HeartDisplay;
import com.example.demo.levels.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing the UserPlane, a type of FighterPlane controlled by the user.
 * The UserPlane can move, fire projectiles, activate shields, and restore health.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = Config.USER_PLANE_IMAGE;
	private static final double Y_UPPER_BOUND = Config.Y_UPPER_BOUND;
	private static final double Y_LOWER_BOUND = Config.Y_LOWER_BOUND;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = Config.USERPLANE_HEIGHT;
	private static final int VERTICAL_VELOCITY = Config.USERPLANE_VERTICAL_VELOCITY;
	private static final double PROJECTILE_X_POSITION = Config.USER_PROJECTILE_X_OFFSET;
	private static final double PROJECTILE_Y_POSITION_OFFSET = Config.USER_PROJECTILE_Y_OFFSET;
	private static final double SHIELD_X_OFFSET = Config.USER_SHIELD_X_OFFSET;
	private static final double SHIELD_Y_OFFSET = Config.USER_SHIELD_Y_OFFSET;
	private static final int MAX_FRAMES_WITH_SHIELD = Config.MAX_FRAMES_WITH_SHIELD;
	private double velocityMultiplier;
	private boolean isShielded;
	private int numberOfKills;
	private int framesWithShieldActivated;
	public static final ShieldEffect SHIELD_EFFECT = new ShieldEffect(INITIAL_X_POSITION, INITIAL_Y_POSITION, IMAGE_HEIGHT);
	private boolean multiShotEnabled = false;
	private long lastFireTime = 0; // Track the last time the user fired a projectile
	private static final long FIRE_COOLDOWN = Config.FIRE_COOLDOWN;

	/**
	 * Constructor for UserPlane.
	 * Initializes the user plane with its image, size, initial position, and initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
	}

	/**
	 * Updates the position of the user plane based on its velocity.
	 * Ensures the plane stays within the vertical bounds.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
		double newPosition = getLayoutY() + getTranslateY();
		SHIELD_EFFECT.setTranslateX(getTranslateX() - SHIELD_X_OFFSET); // Adjust X if necessary
		SHIELD_EFFECT.setTranslateY(getTranslateY() - SHIELD_Y_OFFSET); // Adjust Y if necessary
		if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
			this.setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the state of the user plane by updating its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a single projectile if the cooldown period has passed.
	 *
	 * @return the fired projectile, or null if the cooldown period has not passed
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFireTime >= FIRE_COOLDOWN) {
			lastFireTime = currentTime;
			return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		}
		return null;
	}

	/**
	 * Fires multiple projectiles if multi-shot is enabled and the cooldown period has passed.
	 *
	 * @return a list of fired projectiles, or an empty list if the cooldown period has not passed
	 */
	public List<ActiveActorDestructible> fireMultiShot() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFireTime >= FIRE_COOLDOWN) {
			lastFireTime = currentTime;
			if (multiShotEnabled) {
				double spacing = 20.0; // Example spacing
				double yPos = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
				projectiles.add(new UserProjectile(PROJECTILE_X_POSITION, yPos - spacing));
				projectiles.add(new UserProjectile(PROJECTILE_X_POSITION, yPos));
				projectiles.add(new UserProjectile(PROJECTILE_X_POSITION, yPos + spacing));
			} else {
				// Fall back to the single projectile behavior
				projectiles.add(fireProjectile());
			}
			return projectiles;
		}
		return Collections.emptyList();
	}

	/**
	 * Updates the shield status of the user plane.
	 * Deactivates the shield if the maximum number of frames with the shield activated is reached.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		}
	}

	/**
	 * Moves the user plane up by setting the velocity multiplier to -1.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the user plane down by setting the velocity multiplier to 1.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the user plane by setting the velocity multiplier to 0.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Gets the number of kills made by the user plane.
	 *
	 * @return the number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user plane by the specified count.
	 *
	 * @param count the number of kills to add
	 */
	public void incrementKillCount(int count) {
		this.numberOfKills += count;
	}

	/**
	 * Activates the shield for the user plane.
	 * Adjusts the shield image position and makes it visible.
	 */
	protected void activateShield() {
		isShielded = true;
		SHIELD_EFFECT.activateShield();  // Activate the shield image
		SHIELD_EFFECT.setTranslateX(getTranslateX() - SHIELD_X_OFFSET); // Adjust position relative to the plane
		SHIELD_EFFECT.setTranslateY(getTranslateY() - SHIELD_Y_OFFSET);
		SHIELD_EFFECT.setVisible(true);
	}

	/**
	 * Deactivates the shield for the user plane.
	 * Resets the shield activation frames and makes the shield image invisible.
	 */
	public void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		SHIELD_EFFECT.deactivateShield();  // Deactivate the shield image
		SHIELD_EFFECT.setVisible(false);
	}

	/**
	 * Handles damage taken by the user plane.
	 * Prevents health reduction if the shield is active.
	 */
	@Override
	public void takeDamage() {
		if (isShielded) {
			return; // Prevent health reduction
		}
		super.takeDamage(); // Otherwise, apply damage normally
	}

	/**
	 * Applies true damage to the user plane, bypassing the shield.
	 */
	public void takeTrueDamage() {
		super.takeDamage(); // Apply damage normally
	}

	/**
	 * Restores health to the user plane by the specified amount.
	 * Ensures health does not exceed the maximum allowed.
	 * Updates the heart display accordingly.
	 *
	 * @param amount the amount of health to restore
	 */
	public void restoreHealth(int amount) {
		int newHealth = Math.min(this.health + amount, Config.USER_INITIAL_HEALTH); // Ensure health does not exceed max
		int healthToAdd = newHealth - this.health; // Calculate how many hearts to add
		this.health = newHealth;

		// Access HeartDisplay from LevelView instance
		HeartDisplay heartDisplay = LevelView.getInstance().getHeartDisplay();
		if (healthToAdd > 0) {
			for (int i = 0; i < healthToAdd; i++) {
				heartDisplay.addHeart();
			}
		}
	}

	/**
	 * Enables multi-shot capability for the user plane.
	 * Schedules deactivation of multi-shot after 10 seconds.
	 */
	public void enableMultiShot() {
		this.multiShotEnabled = true;

		// Schedule deactivation after 10 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> this.multiShotEnabled = false));
		timeline.setCycleCount(1);
		timeline.play();
	}

	/**
	 * Checks if multi-shot capability is enabled for the user plane.
	 *
	 * @return true if multi-shot is enabled, false otherwise
	 */
	public boolean isMultiShotEnabled() {
		return multiShotEnabled;
	}
}