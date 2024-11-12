package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

// Custom Observable class
public class MyObservable {
	private final List<MyObserver> observers = new ArrayList<>();

	public void addObserver(MyObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(MyObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers(Object arg) {
		for (MyObserver observer : observers) {
			observer.update(arg);
		}
	}
}
