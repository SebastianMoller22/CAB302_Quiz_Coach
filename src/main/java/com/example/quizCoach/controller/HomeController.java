package com.example.quizCoach.controller;

import com.example.quizCoach.model.AuthenticationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class HomeController {

    @FXML
    private AuthenticationManager authentication;
    @FXML
    private Button logOutButton;
    @FXML
    private Button startNewQuizButton;
    @FXML
    private Button leaderboardButton;
    @FXML
    private Button pastQuizzesButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button settingsButton;

    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @FXML
    private void initialize() {
        System.out.println("Home screen initialized");
    }

    @FXML
    private void handleLeaderboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/leaderboard-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) leaderboardButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleStartNewQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/new-quiz-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) startNewQuizButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/login_screen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 700));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}