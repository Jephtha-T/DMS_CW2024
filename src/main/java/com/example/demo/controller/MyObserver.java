package com.example.demo.controller;

/**
 * Interface for observers in the observer pattern.
 *
 * @param <T> the type of update argument passed to the observer.
 */
public interface MyObserver<T> {
	/**
	 * Called when the observable state changes.
	 *
	 * @param arg the updated data or context.
	 */
	void update(T arg);
}
