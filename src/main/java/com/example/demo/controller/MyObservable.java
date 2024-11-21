package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;


// Custom Observable class
public class MyObservable {
	private final List<MyObserver> observers = new ArrayList<>();
	private boolean hasChanged = false;

	public void addObserver(MyObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(MyObserver observer) {
		observers.remove(observer);
	}

	protected void setChanged() {
		hasChanged = true;
	}

	protected void clearChanged() {
		hasChanged = false;
	}

	// Check if the state has changed
	public boolean hasChanged() {
		return hasChanged;
	}

	public void notifyObservers(Object arg) {
		if (hasChanged) {
			for (MyObserver observer : observers) {
				observer.update(arg);
			}
			clearChanged();  // Reset the changed flag after notifying
		}
	}
}
