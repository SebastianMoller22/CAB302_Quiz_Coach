package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.model.QuizManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;

public class NewQuizController {

    private AuthenticationManager authentication;

    @FXML
    private QuizManager quizManager;

    @FXML
    private TextField topicField;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Button createQuizButton;

    @FXML
    private Spinner<Integer> questionCountSpinner;



    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }


    @FXML
    public void initialize() {
        // Set up spinner with values from 1 to 20
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        questionCountSpinner.setValueFactory(valueFactory);
        quizManager = new QuizManager();
    }

    @FXML
    private void handleCreateQuiz() {
        String topic = topicField.getText();
        int difficulty = (int) difficultySlider.getValue();
        int numQuestions = questionCountSpinner.getValue();
        System.out.println("Number of questions: " + numQuestions);

        System.out.println("Creating quiz on topic: " + topic + " with difficulty: " + difficulty);

        try {
            quizManager.MakeQuiz(topic, difficulty, numQuestions);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/quiz-view.fxml"));
            Parent root = loader.load();
            QuizViewController quizViewController = loader.getController();
            quizViewController.setQuizManager(quizManager.getActivequiz());
            quizViewController.setAuthManager(authentication);
            Stage stage = (Stage) createQuizButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) createQuizButton.getScene().getWindow(); // Get stage from an existing node
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}