package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the Reset Password screen.
 * <p>
 * Allows users to input a new password and confirm it, then redirects them
 * to the login screen. (Currently prints passwords to console for demonstration.)
 */
public class ResetPasswordController {

    /** Input field for the new password. */
    @FXML
    private TextField newPasswordField;

    /** Input field to confirm the new password. */
    @FXML
    private TextField confirmPasswordField;

    /** Button to trigger the password reset process. */
    @FXML
    private Button resetPasswordButton;

    /**
     * Initializes the controller after all @FXML fields are loaded.
     * Sets the action handler for the Reset Password button.
     */
    @FXML
    public void initialize() {
        resetPasswordButton.setOnAction(event -> {
            // Step 1: Get and print entered passwords (for development/testing only)
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            System.out.println("New Password: " + newPassword);
            System.out.println("Confirm Password: " + confirmPassword);

            // TODO: Add password matching & updating logic here

            // Step 2: Navigate back to the login screen
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/login_screen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}