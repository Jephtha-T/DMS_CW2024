package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

interface MyObserver {
	void update(Object arg);
}

public class Controller implements MyObserver {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelTwo";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, IllegalArgumentException, SecurityException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	private void goToLevel(String className) throws ClassNotFoundException, IllegalArgumentException, SecurityException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}

	@Override
	public void update(Object arg) {
		try {
			goToLevel((String) arg);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}
}
