package com.example.demo.Actors;

import com.example.demo.Config;
import com.example.demo.ImageDisplays.HeartDisplay;
import com.example.demo.ImageDisplays.ShieldImage;
import com.example.demo.LevelControl.LevelView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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
	public static final ShieldImage shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION, IMAGE_HEIGHT);
	private boolean multiShotEnabled = false;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
		double newPosition = getLayoutY() + getTranslateY();
		shieldImage.setTranslateX(getTranslateX() - SHIELD_X_OFFSET); // Adjust X if necessary
		shieldImage.setTranslateY(getTranslateY() - SHIELD_Y_OFFSET); // Adjust Y if necessary
		if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
			this.setTranslateY(initialTranslateY);
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (multiShotEnabled) {
			double spacing = 20.0; // Example spacing
			double yPos = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			new UserProjectile(PROJECTILE_X_POSITION, yPos - spacing);
			new UserProjectile(PROJECTILE_X_POSITION, yPos);
			new UserProjectile(PROJECTILE_X_POSITION, yPos + spacing);
			return null; // Return null since we spawn multiple projectiles
		}
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	public List<ActiveActorDestructible> fireMultiShot() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();
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


	private void updateShield() {

		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		}
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount(int count) {
		this.numberOfKills+= count;
	}

	protected void activateShield() {
		isShielded = true;
		shieldImage.activateShield();  // Activate the shield image
		shieldImage.setTranslateX(getTranslateX() - SHIELD_X_OFFSET); // Adjust position relative to the plane
		shieldImage.setTranslateY(getTranslateY() - SHIELD_Y_OFFSET);
		shieldImage.setVisible(true);
	}

	public void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.deactivateShield();  // Deactivate the shield image
		shieldImage.setVisible(false);
	}

	@Override
	public void takeDamage() {
		if (isShielded) {
			return; // Prevent health reduction
		}
		super.takeDamage(); // Otherwise, apply damage normally
	}

	public void takeTrueDamage() {
		super.takeDamage(); // Otherwise, apply damage normally
	}

	public boolean isShielded() {
		return isShielded;
	}

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

	public void enableMultiShot() {
		this.multiShotEnabled = true;

		// Schedule deactivation after 10 seconds
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
			this.multiShotEnabled = false;
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}

	public boolean isMultiShotEnabled() {
		return  multiShotEnabled;
	}
}
