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

/**
 * Controller for the home screen of the IntelliTutor application.
 * Provides navigation to various sections such as starting a new quiz,
 * viewing past quizzes, the leaderboard, and user profile settings.
 */
public class HomeController {

    /** The active session manager holding user state and authentication context. */
    @FXML
    private SessionManager sessionManager;

    /** Button to trigger logout functionality and return to the login screen. */
    @FXML
    private Button logOutButton;

    /** Button to navigate to the New Quiz creation screen. */
    @FXML
    private Button startNewQuizButton;

    /** Button to open the leaderboard screen. */
    @FXML
    private Button leaderboardButton;

    /** Button to show past quizzes the user has completed. */
    @FXML
    private Button pastQuizzesButton;

    /** Button to access the profile update screen. */
    @FXML
    private Button profileButton;

    /** Optional settings button (currently unused). */
    @FXML
    private Button settingsButton;

    /**
     * Injects the active session manager for maintaining user context across screens.
     * @param session the session manager instance to use
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /**
     * Initializes the home screen. Called automatically by the JavaFX framework.
     */
    @FXML
    private void initialize() {
        System.out.println("Home screen initialized");
    }

    /**
     * Handles navigation to the leaderboard screen.
     * Passes the current session manager to the new screen's controller.
     */
    @FXML
    private void handleLeaderboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/leaderboard-view.fxml"));
            Parent root = loader.load();
            LeaderboardController leaderboard = loader.getController();
            leaderboard.setSessionManager(sessionManager);
            Stage stage = (Stage) leaderboardButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles navigation to the new quiz creation screen.
     * Ensures the session context is preserved across views.
     */
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

    /**
     * Logs out the current user and returns them to the login screen.
     * Invokes the authentication manager to clear the session state.
     */
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

    /**
     * Opens the user's profile settings screen, passing along session context.
     */
    @FXML
    private void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/profile-view.fxml"));
            Parent root = loader.load();
            ProfileController profile = loader.getController();
            profile.setSessionManager(sessionManager);
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a list of past quizzes taken by the user.
     * Keeps the session active by transferring the session manager.
     */
    @FXML
    private void handlePastQuizzes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/PastQuizzes.fxml"));
            Parent root = loader.load();
            PastQuizzesController pqController = loader.getController();
            pqController.setSessionManager(sessionManager);
            Stage stage = (Stage) pastQuizzesButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}