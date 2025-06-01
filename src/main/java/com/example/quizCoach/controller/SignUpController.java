package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Sign-Up screen.
 * <p>
 * Handles user input validation, sign-up logic, and screen navigation.
 */
public class SignUpController {

    /** Manager responsible for authentication-related operations. */
    @FXML private AuthenticationManager authentication;

    /** Text field for the user's email address. */
    @FXML private TextField emailField;

    /** Text field for the user's chosen username. */
    @FXML private TextField usernameField;

    /** Password field for the user's chosen password. */
    @FXML private PasswordField passwordField;

    /** Button to trigger the sign-up process. */
    @FXML private Button signUpButton;

    /** Link to return to the login screen for existing users. */
    @FXML private Hyperlink existingAccountLink;

    /** Label used to display error messages to the user. */
    @FXML private Label errorMessage;

    /**
     * Injects the {@link AuthenticationManager} used for signing up users.
     *
     * @param authentication the AuthenticationManager instance
     */
    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    /**
     * Initializes the UI components and sets up event handlers for user actions.
     * <p>
     * Validates input fields and navigates the user to the login screen on success.
     */
    @FXML
    public void initialize() {
        signUpButton.setOnAction(e -> {
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
                errorMessage.setText("Password must be at least 8 characters long and include "
                        + "uppercase, lowercase, number & special character.");
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