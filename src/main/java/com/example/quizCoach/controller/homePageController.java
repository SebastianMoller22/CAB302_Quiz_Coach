package com.example.quizCoach.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class homePageController {

    @FXML private Button startNewQuizButton;
    @FXML private Label pastScoresLabel;
    @FXML private Label nextQuizLabel;
    @FXML private Label pastQuizzesLabel;

    @FXML
    public void initialize() {
        // Start New Quiz Button Action
        startNewQuizButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/start_quiz.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) startNewQuizButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Past Scores Label Click Action
        pastScoresLabel.setOnMouseClicked((MouseEvent e) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/past_scores.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) pastScoresLabel.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Next Quiz Label Click Action
        nextQuizLabel.setOnMouseClicked((MouseEvent e) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/next_quiz.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) nextQuizLabel.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Past Quizzes Label Click Action
        pastQuizzesLabel.setOnMouseClicked((MouseEvent e) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/past_quizzes.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) pastQuizzesLabel.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
