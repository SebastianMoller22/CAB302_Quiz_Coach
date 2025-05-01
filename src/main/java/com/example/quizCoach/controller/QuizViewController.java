package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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

        addMultipleChoiceQuestion("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"});

        addMultipleChoiceQuestion("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"});

        addMultipleChoiceQuestion("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"});

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/results-view.fxml"));
            Parent root = loader.load();

            // Pass the results string to the results page
            ResultsController controller = loader.getController();
            controller.setResultsText("✅ You got 2/2 correct!\nExcellent performance.");

            Stage stage = (Stage) seeResultsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
