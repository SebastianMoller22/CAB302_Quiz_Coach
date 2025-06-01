package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.model.QuizManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class NewQuizController {

    private SessionManager sessionManager;

    Thread quizcreation;

    @FXML
    private TextField topicField;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Button createQuizButton;

    @FXML
    private Spinner<Integer> questionCountSpinner;



    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }
    boolean quiz_start = false;
    private Timeline timeleine;

    @FXML
    public void initialize() {
        // Set up spinner with values from 1 to 20
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        questionCountSpinner.setValueFactory(valueFactory);

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
            if(quizcreation.isAlive()){

            }
            else{


            }
        }
    }

    @FXML
    private void handleCreateQuiz(ActionEvent event) throws IOException {
        if (quiz_start == false) {
            String topic = topicField.getText();
            int difficulty = (int) difficultySlider.getValue();
            int numQuestions = questionCountSpinner.getValue();
            System.out.println("Number of questions: " + numQuestions);

            System.out.println("Creating quiz on topic: " + topic + " with difficulty: " + difficulty);

            Task<Void> quizTask = sessionManager.getQuizManager().generateQuizTask(topic, difficulty, numQuestions);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            quizTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quiz Ready");
                    alert.setHeaderText(null);
                    alert.setContentText("Your quiz has been successfully generated.");
                    alert.showAndWait();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/quiz-view.fxml"));
                        Parent root = loader.load();
                        QuizViewController quizview = loader.getController();
                        quizview.setSessionManager(sessionManager);
                        stage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            });

            quizcreation = new Thread(quizTask);

            quizcreation.start();

            quiz_start = true;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController home = loader.getController();
            home.setSessionManager(sessionManager);
            stage.setScene(new Scene(root));
        }

    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setSessionManager(sessionManager);
            Stage stage = (Stage) createQuizButton.getScene().getWindow(); // Get stage from an existing node
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}