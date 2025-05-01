package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class QuizViewController {

    @FXML
    private VBox quizContainer;

    @FXML
    private Button seeResultsButton;

    @FXML
    public void initialize() {
        // Sample questions (could be AI-generated later)
        addMultipleChoiceQuestion("What is the capital of France?",
                new String[]{"Berlin", "Madrid", "Paris", "London"});

        addMultipleChoiceQuestion("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"});
    }

    private void addMultipleChoiceQuestion(String questionText, String[] options) {
        Label questionLabel = new Label(questionText);
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        ToggleGroup group = new ToggleGroup();
        VBox questionBox = new VBox(5);
        questionBox.getChildren().add(questionLabel);

        for (String option : options) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.setStyle("-fx-text-fill: white;");
            questionBox.getChildren().add(rb);
        }

        quizContainer.getChildren().add(questionBox);
    }

    @FXML
    private void handleSeeResults() {
        System.out.println("See Results button clicked.");
        // TODO: Evaluate selections or navigate to results page
    }
}
