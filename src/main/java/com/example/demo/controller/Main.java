package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.LevelControl.LevelManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private static final String MAIN_MENU_FXML = Config.MAIN_MENU_FXML;


	@Override
	public void start(Stage stage) throws Exception {
		// Initialize the singleton LevelManager
		LevelManager.initialize(stage);

		// Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_FXML));
		Parent root = loader.load();

		// Set up the controller
		MainMenuController controller = loader.getController();
		controller.setStage(stage);

		// Set up the scene
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}