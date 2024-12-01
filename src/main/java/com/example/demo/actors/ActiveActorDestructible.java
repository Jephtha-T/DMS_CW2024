package com.example.demo.actors;

public abstract class ActiveActorDestructible extends ActiveActor{

	private boolean isDestroyed;

	protected ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	public void destroy() {
		this.isDestroyed = true;
	}

    public void takeDamage() {
    }

	public boolean isDestroyed() {
		return isDestroyed;
	}

}
