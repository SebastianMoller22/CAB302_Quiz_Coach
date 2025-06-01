package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Controller for the Results View screen.
 * <p>
 * Displays the user's quiz results and allows them to return to the home page.
 */
public class ResultsController {

    /** Reference to the current session, which provides access to user and quiz data. */
    private SessionManager sessionManager;

    /** Text area to display result summary and feedback. */
    @FXML
    private TextArea resultsTextArea;

    /**
     * Sets the text to be shown in the results display area.
     *
     * @param text Result message to display
     */
    public void setResultsText(String text) {
        resultsTextArea.setText(text);
    }

    /**
     * Injects the active session manager instance.
     *
     * @param session the session to use
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /**
     * Handles the "Back" button.
     * Navigates the user to the Home screen and resets quiz state.
     */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();

            // Reset quiz state before returning home
            sessionManager.getQuizManager().return_home();

            HomeController homeController = loader.getController();
            homeController.setSessionManager(sessionManager);

            Stage stage = (Stage) resultsTextArea.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
