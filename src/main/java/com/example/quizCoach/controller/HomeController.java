package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.authentication.AuthenticationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class HomeController {

    @FXML
    private SessionManager sessionManager;
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

    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
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
            NewQuizController newQuizController = loader.getController();
            newQuizController.setSessionManager(sessionManager);
            Stage stage = (Stage) startNewQuizButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut() {
        try {
            sessionManager.getAuthenticationManager().Logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/login_screen.fxml"));
            Parent root = loader.load();
            loginController LoginController = loader.getController();
            LoginController.setAuthManager(sessionManager.getAuthenticationManager());
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 700));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/profile-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) profileButton.getScene().getWindow(); // Use a defined node
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}