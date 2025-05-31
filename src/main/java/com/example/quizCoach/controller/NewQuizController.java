package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.model.QuizManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

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
    boolean quiz_start = false;
    private Timeline timeleine;

    @FXML
    public void initialize() {
        // Set up spinner with values from 1 to 20
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        questionCountSpinner.setValueFactory(valueFactory);
        quizManager = new QuizManager();

        /*
        make a loop that checks if a quiz is being made aver sec nd and if it is done before changeing to the next scene
         */
        timeleine = new Timeline(new KeyFrame(Duration.seconds(1),this::handleKeyframe));
        timeleine.setCycleCount(Animation.INDEFINITE);
        timeleine.play();
    }

    /*
    scene changer handle
     */
    private void handleKeyframe(ActionEvent event){
        if (quiz_start){
            if(quizManager.isAlive()){

            }
            else{

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/quiz-view.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                QuizViewController quizViewController = loader.getController();
                quizViewController.setQuizManager(quizManager.getActivequiz());
                quizViewController.setAuthManager(authentication);
                Stage stage = (Stage) createQuizButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                timeleine.stop();
            }
        }
    }

    @FXML
    private void handleCreateQuiz() {
        if (quiz_start == false) {
            String topic = topicField.getText();
            int difficulty = (int) difficultySlider.getValue();
            int numQuestions = questionCountSpinner.getValue();
            System.out.println("Number of questions: " + numQuestions);

            System.out.println("Creating quiz on topic: " + topic + " with difficulty: " + difficulty);

            quizManager.MakeQuiz(topic, difficulty, numQuestions);
            quizManager.start();
            quiz_start = true;
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