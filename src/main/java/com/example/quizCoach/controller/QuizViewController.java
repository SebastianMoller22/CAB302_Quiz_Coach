package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class QuizViewController {

    @FXML
    private TextArea quizDisplayArea;

    @FXML
    private Button seeResultsButton;

    @FXML
    public void initialize() {
        // This would be dynamically generated from AI or other logic
        quizDisplayArea.setText("1. What is the capital of France?\nA. London\nB. Berlin\nC. Paris\nD. Madrid");
    }

    @FXML
    private void handleSeeResults() {
        System.out.println("See Results button clicked.");
        // TODO: Show results page or dialog
    }
}
