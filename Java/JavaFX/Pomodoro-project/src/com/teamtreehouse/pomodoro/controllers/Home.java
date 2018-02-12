package com.teamtreehouse.pomodoro.controllers;

import com.teamtreehouse.pomodoro.model.Attempt;
import com.teamtreehouse.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Home {
    @FXML
    private VBox container;

    @FXML
    private Label title;

    @FXML
    private TextArea message;

    private Attempt currentAttempt;
    private StringProperty timerText;
    private Timeline timeline;
    private final AudioClip tada;


    public Home() {
        timerText = new SimpleStringProperty();
        setTimerText(0);
        tada = new AudioClip(getClass().getResource("/sounds/Ta-Da.mp3").toExternalForm());
        tada.setVolume(0.075);
    }

    public String getTimerText() {
        return timerText.get();
    }

    public StringProperty timerTextProperty() {
        return timerText;
    }

    public void setTimerText(String timerText) {
        this.timerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d", minutes, seconds));
    }

    private void prepareAttempt(AttemptKind kind) {
        reset();
        currentAttempt = new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setTimerText(currentAttempt.getRemainingSeconds());
        timeline = new Timeline();
        timeline.setCycleCount(kind.getTotalSeconds());
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            currentAttempt.tick();
            setTimerText(currentAttempt.getRemainingSeconds());
        }));
        timeline.setOnFinished(e -> {
            saveCurrentAttempt();
            tada.play();
            prepareAttempt(currentAttempt.getKind() == AttemptKind.FOCUS ? AttemptKind.BREAK : AttemptKind.FOCUS);
        });
    }

    private void saveCurrentAttempt() {
        currentAttempt.setMessage(message.getText());
        currentAttempt.save();
    }

    private void reset() {
        clearAttemptStyles();
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
        }
    }

    public void playTimer() {
        container.getStyleClass().add("playing");
        timeline.play();
    }

    public void pauseTimer() {
        container.getStyleClass().remove("playing");
        timeline.pause();
    }


    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());
    }


    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
        for (AttemptKind kind : AttemptKind.values()) {
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }

/*    public void DEBUG(ActionEvent actionEvent) {
        System.out.println("Hello World!");
    }*/

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if (currentAttempt == null) {
            handleRestart(actionEvent);
        } else {
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
