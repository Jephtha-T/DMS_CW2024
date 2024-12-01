package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.imagedisplay.GameOverImage;
import com.example.demo.imagedisplay.HeartDisplay;
import com.example.demo.imagedisplay.WinImage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Manages the display elements for a level, including the heart display, win image, game over image, and kill count.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = Config.HEART_DISPLAY_X;
	private static final double HEART_DISPLAY_Y_POSITION = Config.HEART_DISPLAY_Y;
	private static final int WIN_IMAGE_X_POSITION = Config.WIN_IMAGE_X_POSITION;
	private static final int WIN_IMAGE_Y_POSITION = Config.WIN_IMAGE_Y_POSITION;
	private static final int LOSS_SCREEN_X_POSITION = Config.GAME_OVER_X_POSITION;
	private static final int LOSS_SCREEN_Y_POSITION = Config.GAME_OVER_Y_POSITION;
	private final Group mRoot; // The root group for adding display elements
	private final WinImage winImage; // The win image display
	private final GameOverImage gameOverImage; // The game over image display
	private final HeartDisplay heartDisplay; // The heart display
	private int killCount; // The current kill count
	public final Text killCountText; // The text display for the kill count
	private final int killsToAdvance; // The number of kills required to advance to the next level
	private static LevelView instance; // Static instance for global access

	/**
	 * Constructor for LevelView.
	 * Initializes the display elements with the specified root group, number of hearts to display, and kills to advance.
	 *
	 * @param mRoot the root group for adding display elements
	 * @param heartsToDisplay the number of hearts to display
	 * @param killsToAdvance the number of kills required to advance to the next level
	 */
	public LevelView(Group mRoot, int heartsToDisplay, int killsToAdvance) {
		instance = this; // Assign the instance when constructed
		this.mRoot = mRoot;
		this.killsToAdvance = killsToAdvance;
		this.killCount = 0;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.killCountText = new Text();
		Font customFont = Font.loadFont(getClass().getResourceAsStream(Config.FONT_TTF), 20);

		this.killCountText.setFont(customFont);
		killCountText.setFill(Color.WHITE); // Set text color to white
		this.killCountText.setX(Config.KILL_COUNT_TEXT_X);
		this.killCountText.setY(Config.KILL_COUNT_TEXT_Y);
		this.killCountText.setText("Kills: 0");
	}

	/**
	 * Displays the heart display on the screen.
	 */
	public void showHeartDisplay() {
		mRoot.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen.
	 */
	public void showWinImage() {
		mRoot.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the kill count text on the screen.
	 * Ensures the text is at the top of the display.
	 */
	public void showKillCount() {
		mRoot.getChildren().remove(killCountText); // Remove if it exists
		mRoot.getChildren().add(killCountText);   // Re-add to ensure it's at the top
		killCountText.setVisible(true);
	}

	/**
	 * Displays the game over image on the screen.
	 */
	public void showGameOverImage() {
		mRoot.getChildren().add(gameOverImage);
		gameOverImage.showGameOverImage();
	}

	/**
	 * Updates the kill count text with the new kill count.
	 *
	 * @param newKillCount the new kill count
	 */
	public void updateKillCount(int newKillCount) {
		this.killCount = newKillCount;
		if (killsToAdvance != 0) {
			this.killCountText.setText("Kills: " + killCount + " / " + killsToAdvance);
		} else {
			this.killCountText.setText("Kills: " + killCount);
		}
	}

	/**
	 * Removes hearts from the heart display based on the remaining hearts.
	 *
	 * @param heartsRemaining the number of hearts remaining
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Gets the static instance of LevelView.
	 *
	 * @return the static instance of LevelView
	 */
	public static LevelView getInstance() {
		return instance;
	}

	/**
	 * Gets the heart display.
	 *
	 * @return the heart display
	 */
	public HeartDisplay getHeartDisplay() {
		return heartDisplay;
	}
}