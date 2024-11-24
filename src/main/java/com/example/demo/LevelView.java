package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = Config.HEART_DISPLAY_X;
	private static final double HEART_DISPLAY_Y_POSITION = Config.HEART_DISPLAY_Y;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
	private final Group mRoot;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	public final Text killCountText;
	
	public LevelView(Group mRoot, int heartsToDisplay) {
		this.mRoot = mRoot;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
		this.killCountText = new Text("Kills: 0");
		this.killCountText.setFont(Font.font("Verdana", 20));
		this.killCountText.setFill(Color.WHITE);
		killCountText.setX(Config.KILL_COUNT_TEXT_X);
		killCountText.setY(Config.KILL_COUNT_TEXT_Y);
		mRoot.getChildren().add(killCountText);

	}
	
	public void showHeartDisplay() {
		mRoot.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		mRoot.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		mRoot.getChildren().add(gameOverImage);
	}

	public void updateKillCount(int kills) {

		this.killCountText.setText("Kills: " + kills);

	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
