package com.example.demo.controller;

import com.example.demo.LevelManager;

import javafx.stage.Stage;


public class Controller{

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final LevelManager levelManager;

	public Controller(Stage stage) {
		this.levelManager = new LevelManager(stage);
	}

	public void launchGame() throws Exception {
		levelManager.loadLevel(LEVEL_ONE_CLASS_NAME);
	}
}
