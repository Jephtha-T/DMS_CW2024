package com.example.demo.actors;

/**
 * Abstract class representing a destructible active actor in the game.
 * Extends ActiveActor to include destruction and damage handling.
 */
public abstract class ActiveActorDestructible extends ActiveActor {

	protected boolean isDestroyed;

	/**
	 * Constructor for ActiveActorDestructible.
	 * Initializes the actor's image, position, size, and destruction state.
	 *
	 * @param imageName   the name of the image file
	 * @param imageHeight the height of the image
	 * @param initialXPos the initial X position of the actor
	 * @param initialYPos the initial Y position of the actor
	 */
	protected ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Abstract method to update the position of the actor.
	 * Must be implemented by subclasses.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the actor's state.
	 * Must be implemented by subclasses.
	 */
	public abstract void updateActor();

	/**
	 * Marks the actor as destroyed.
	 */
	public void destroy() {
		this.isDestroyed = true;
	}

	/**
	 * Abstract method to handle damage taken by the actor.
	 * Must be implemented by subclasses.
	 */
	public abstract void takeDamage();

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}