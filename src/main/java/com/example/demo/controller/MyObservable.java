package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;


public class MyObservable {
	private final List<MyObserver> observers = new ArrayList<>();
	private boolean hasChanged = false;

	public void addObserver(MyObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(MyObserver observer) {
		observers.remove(observer);
	}

	public void setChanged() {
		hasChanged = true;
	}

	protected void clearChanged() {
		hasChanged = false;
	}

	public boolean hasChanged() {
		return hasChanged;
	}

	public void notifyObservers(Object arg) {
		if (hasChanged) {
			for (MyObserver observer : observers) {
				observer.update(arg);
			}
			clearChanged();
		}
	}
}
