package com.example.demo.controller;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

/**
 * Class representing an observable object, or "data" in the model-view paradigm.
 * It can be observed by implementing the MyObserver interface.
 */
public class MyObservable {
	private final List<MyObserver> observers = new CopyOnWriteArrayList<>();
	private boolean hasChanged = false;

	/**
	 * Adds an observer to the list of observers.
	 *
	 * @param observer the observer to be added
	 */
	public void addObserver(MyObserver observer) {
		observers.add(observer);
	}

	/**
	 * Removes an observer from the list of observers.
	 *
	 * @param observer the observer to be removed
	 */
	public void removeObserver(MyObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Marks this Observable object as having been changed.
	 */
	public void setChanged() {
		hasChanged = true;
	}

	/**
	 * Clears the changed status of this Observable object.
	 */
	protected void clearChanged() {
		hasChanged = false;
	}

	/**
	 * If this object has changed, notifies all of its observers.
	 * Each observer's update method is called with the specified argument.
	 *
	 * @param arg the argument passed to the update method of each observer
	 */
	public void notifyObservers(Object arg) {
		if (hasChanged) {
			for (MyObserver observer : observers) {
				observer.update(arg);
			}
			clearChanged();
		}
	}
}