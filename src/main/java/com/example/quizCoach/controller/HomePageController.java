package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class HomePageController {

    @FXML
    private Hyperlink pastScoresLink;

    @FXML
    private Hyperlink nextQuizLink;

    @FXML
    private Hyperlink pastQuizzesLink;

    @FXML
    private Button startNewQuizButton;

    @FXML
    public void initialize() {
        // Initialization logic can go here if needed
    }

    @FXML
    private void handlePastScoresLink(ActionEvent event) {
        // Logic to handle past scores link click
        System.out.println("Past Scores link clicked");
    }

    @FXML
    private void handleNextQuizLink(ActionEvent event) {
        // Logic to handle next quiz link click
        System.out.println("Next Quiz link clicked");
    }

    @FXML
    private void handlePastQuizzesLink(ActionEvent event) {
        // Logic to handle past quizzes link click
        System.out.println("Past Quizzes link clicked");
    }

    @FXML
    private void handleStartNewQuizButton(ActionEvent event) {
        // Logic to start a new quiz
        System.out.println("Start a New Quiz button clicked");
    }
}