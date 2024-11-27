package com.example.demo.LevelControl;

import com.example.demo.Config;
import com.example.demo.ImageDisplays.GameOverImage;
import com.example.demo.ImageDisplays.HeartDisplay;
import com.example.demo.ImageDisplays.WinImage;
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
	private int killCount;
	public final Text killCountText;
	
	public LevelView(Group mRoot, int heartsToDisplay) {
		this.mRoot = mRoot;
		this.killCount = 0;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
		this.killCountText = new Text();
		this.killCountText.setFont(new Font("Arial", 20));
		killCountText.setFill(Color.BLACK); // Set text color to white
		this.killCountText.setX(Config.KILL_COUNT_TEXT_X);
		this.killCountText.setY(Config.KILL_COUNT_TEXT_Y);
		this.killCountText.setText("Kills: 0");

	}
	
	public void showHeartDisplay() {
		mRoot.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		mRoot.getChildren().add(winImage);
		winImage.showWinImage();
	}

	public void showKillCount(){
		mRoot.getChildren().add(killCountText);
		killCountText.setVisible(true);
	}
	
	public void showGameOverImage() {
		mRoot.getChildren().add(gameOverImage);
	}

	public void updateKillCount(int newKillCount) {
		this.killCount = newKillCount;
		this.killCountText.setText("Kills: " + killCount);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
