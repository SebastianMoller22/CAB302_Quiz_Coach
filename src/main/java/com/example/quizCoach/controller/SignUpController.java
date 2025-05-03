package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML private AuthenticationManager authentication;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink existingAccountLink;
    @FXML private Label errorMessage;

    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @FXML
    public void initialize() {
        signUpButton.setOnAction(e -> {
            // Clear any previous error
            errorMessage.setText("");
            errorMessage.setVisible(false);

            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Validate username format
            if (!ValidationUtils.isValidUsername(username)) {
                errorMessage.setText("Username must start with a letter, be 3–20 characters long, "
                        + "and contain only letters, digits, or underscores.");
                errorMessage.setVisible(true);
                return;
            }

            // Validate email format
            if (!ValidationUtils.isValidEmail(email)) {
                errorMessage.setText("Please enter a valid email address.");
                errorMessage.setVisible(true);
                return;
            }

            // Validate password strength
            if (!ValidationUtils.isValidPassword(password)) {
                errorMessage.setText(
                        "Password must be at least 8 characters long and include uppercase, lowercase, "
                        + "number & special character."
                );
                errorMessage.setVisible(true);
                return;
            }

            // Attempt signup
            try {
                authentication.Signup(username, email, password);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/example/quizCoach/login_screen.fxml"));
                Parent root = loader.load();
                loginController LoginController = loader.getController();
                LoginController.setAuthManager(authentication);
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (Exception ex) {
                errorMessage.setText("Signup failed: " + ex.getMessage());
                errorMessage.setVisible(true);
            }
        });

        existingAccountLink.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/example/quizCoach/login_screen.fxml"));
                Parent root = loader.load();
                loginController LoginController = loader.getController();
                LoginController.setAuthManager(authentication);
                Stage stage = (Stage) existingAccountLink.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}