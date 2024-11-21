package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

interface MyObserver {
	void update(Object arg);
}

public class Controller implements MyObserver {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws Exception {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws Exception {
		System.out.println("Loading level: " + className);

		// Dynamically load the next level
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		myLevel.addObserver(this); // Add this controller as an observer

		// Initialize the scene for the new level
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene); // Set the new scene on the stage

		// Start the new level
		myLevel.startGame();

		System.out.println("Level " + className + " started successfully.");
	}

	@Override
	public void update(Object arg) {
		try {
			System.out.println("Update received for next level: " + arg); // Debugging
			goToLevel((String) arg); // Transition to the next level
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Error loading level: " + e.getMessage());
			alert.show();
		}
	}
}
