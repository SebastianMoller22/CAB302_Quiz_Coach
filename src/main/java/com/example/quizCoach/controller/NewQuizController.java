package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class NewQuizController {

    @FXML
    private TextField topicField;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Button createQuizButton;

    @FXML
    private void handleCreateQuiz() {
        String topic = topicField.getText();
        int difficulty = (int) difficultySlider.getValue();

        System.out.println("Creating quiz on topic: " + topic + " with difficulty: " + difficulty);

        // TODO: Send data to quiz generator / next page
    }
}