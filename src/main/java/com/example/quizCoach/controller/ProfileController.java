package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.authentication.AuthenticationConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ProfileController {

    private SessionManager sessionManager;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    @FXML
    private void handleSave() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();
        boolean passwordupdated = false;

        // TODO: Add your update logic here (e.g., update database or model)
        if (sessionManager.getAuthenticationManager().validateString(newUsername, AuthenticationConstant.usernameRegex)) {
            sessionManager.getAuthenticationManager().getActiveUser().setUsername(newUsername);
        }
        if (sessionManager.getAuthenticationManager().validateString(newEmail, AuthenticationConstant.emailRegex)) {
            sessionManager.getAuthenticationManager().getActiveUser().setEmail(newEmail);
            passwordupdated = true;
        }
        if (sessionManager.getAuthenticationManager().validateString(newPassword, AuthenticationConstant.passwordRegex)) {
            sessionManager.getAuthenticationManager().getActiveUser().setUsername(newPassword);
        }
        sessionManager.getAuthenticationManager().updateActiveUser(passwordupdated);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Updated");
        alert.setHeaderText(null);
        alert.setContentText("Your profile has been updated.");
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController home = loader.getController();
            home.setSessionManager(sessionManager);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}