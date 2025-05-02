package com.example.quizCoach.controller;

import com.example.quizCoach.model.AuthenticationManager;
import com.example.quizCoach.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML private AuthenticationManager authentication = new AuthenticationManager();
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink existingAccountLink;
    @FXML private Label errorMessage;

    @FXML
    public void initialize() {
        // Ensure AuthenticationManager is initialized
        if (authentication == null) {
            authentication = new AuthenticationManager();
        }

        signUpButton.setOnAction(e -> {
            // Clear any previous error
            errorMessage.setText("");
            errorMessage.setVisible(false);

            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Validate email format
            if (!ValidationUtils.isValidEmail(email)) {
                errorMessage.setText("🚫 Please enter a valid email address.");
                errorMessage.setVisible(true);
                return;
            }

            // Validate password strength
            if (!ValidationUtils.isValidPassword(password)) {
                errorMessage.setText(
                        "🚫 Password must be at least 8 characters long and include uppercase, lowercase, number & special character."
                );
                errorMessage.setVisible(true);
                return;
            }

            // Attempt signup
            try {
                authentication.Signup(username, email, password);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/login_screen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (Exception ex) {
                errorMessage.setText("⚠️ Signup failed: " + ex.getMessage());
                errorMessage.setVisible(true);
            }
        });

        existingAccountLink.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/login_screen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) existingAccountLink.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}