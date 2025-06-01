package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * Controller for the New Quiz creation page.
 * Allows the user to specify quiz topic, difficulty, and number of questions.
 * Handles asynchronous quiz generation with visual feedback.
 */
public class NewQuizController {

    private SessionManager sessionManager;
    private Thread quizCreationThread;
    private boolean isQuizBeingCreated = false;

    @FXML private TextField topicField;
    @FXML private Slider difficultySlider;
    @FXML private Button createQuizButton;
    @FXML private Spinner<Integer> questionCountSpinner;

    private Timeline creationWatcher;

    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    @FXML
    public void initialize() {
        // Initialize Spinner for question count (1–20)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        questionCountSpinner.setValueFactory(valueFactory);

        // Start polling to check when the quiz generation thread finishes
        creationWatcher = new Timeline(new KeyFrame(Duration.seconds(1), this::handleKeyframe));
        creationWatcher.setCycleCount(Animation.INDEFINITE);
        creationWatcher.play();
    }

    /**
     * Periodically checks if the background quiz generation thread has completed.
     */
    private void handleKeyframe(javafx.event.ActionEvent event) {
        if (isQuizBeingCreated && quizCreationThread != null && !quizCreationThread.isAlive()) {
            isQuizBeingCreated = false;  // Reset flag to avoid repeated scene switches

            Platform.runLater(() -> {
                try {
                    // Show success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quiz Ready");
                    alert.setHeaderText(null);
                    alert.setContentText("Your quiz has been successfully generated.");
                    alert.showAndWait();

                    // Navigate to quiz view
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/quiz-view.fxml"));
                    Parent root = loader.load();
                    QuizViewController quizView = loader.getController();
                    quizView.setSessionManager(sessionManager);

                    Stage stage = (Stage) createQuizButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * Called when the user clicks the "Create Quiz" button.
     * Starts the quiz generation process asynchronously.
     */
    @FXML
    private void handleCreateQuiz(javafx.event.ActionEvent event) throws IOException {
        if (!isQuizBeingCreated) {
            String topic = topicField.getText();
            int difficulty = (int) difficultySlider.getValue();
            int numQuestions = questionCountSpinner.getValue();

            System.out.println("Creating quiz on topic: " + topic + " with difficulty: " + difficulty);

            Task<Void> quizTask = sessionManager.getQuizManager().generateQuizTask(topic, difficulty, numQuestions);

            quizTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Quiz Generation Failed");
                    errorAlert.setHeaderText("An error occurred");
                    errorAlert.setContentText("Please try again.");
                    errorAlert.showAndWait();
                });
            });

            // Assign the thread to the global variable so it can be polled
            quizCreationThread = new Thread(quizTask);
            quizCreationThread.start();
            isQuizBeingCreated = true;
        }
    }

    /**
     * Returns to the Home Page.
     */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setSessionManager(sessionManager);
            Stage stage = (Stage) createQuizButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}