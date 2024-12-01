package com.example.demo.levels;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Class representing the game loop.
 * Manages the execution of a repeated action at a specified interval.
 */
public class GameLoop {
    private final Timeline timeline; // Timeline for managing the game loop

    /**
     * Constructor for GameLoop.
     * Initializes the game loop with the specified action and interval.
     *
     * @param action the action to be executed at each interval
     * @param intervalMilliseconds the interval in milliseconds between each execution of the action
     */
    public GameLoop(Runnable action, int intervalMilliseconds) {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(intervalMilliseconds), e -> action.run()));
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Starts the game loop.
     * Begins the execution of the action at the specified interval.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the game loop.
     * Halts the execution of the action.
     */
    public void stop() {
        timeline.stop();
    }
}