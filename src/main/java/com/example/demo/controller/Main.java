package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.levels.LevelManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the application, extending JavaFX Application.
 * This class sets up the main stage and loads the initial FXML file.
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = Config.SCREEN_WIDTH;
	private static final int SCREEN_HEIGHT = Config.SCREEN_HEIGHT;
	private static final String TITLE = Config.TITLE;
	private static final String MAIN_MENU_FXML = Config.MAIN_MENU_FXML;

	/**
	 * Starts the JavaFX application.
	 * Initializes the LevelManager, loads the main menu FXML, and sets up the main stage.
	 *
	 * @param stage the primary stage for this application
	 * @throws Exception if the FXML file cannot be loaded
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Initialize the singleton LevelManager
		LevelManager.initialize(stage);

		// Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_FXML));
		Parent root = loader.load();

		MainMenuController.initialize(stage);

		// Set up the scene
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * The main method for launching the JavaFX application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch();
	}
}