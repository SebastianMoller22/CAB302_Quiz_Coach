package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the Forgot Password screen in IntelliTutor.
 * Handles navigation to reset password, login, and sign-up screens.
 */
public class ForgotPasswordController {

    /** TextField where the user enters their username. */
    @FXML private TextField usernameField;

    /** Button to initiate password reset and navigate to the reset screen. */
    @FXML private Button resetButton;

    /** Hyperlink to return to the existing account (login) screen. */
    @FXML private Hyperlink existingAccountLink;

    /** Hyperlink to navigate to the account creation (sign-up) screen. */
    @FXML private Hyperlink createAccountLink;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up event handlers for all navigation elements on the screen.
     */
    @FXML
    public void initialize() {
        resetButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/reset_password.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) resetButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
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

        createAccountLink.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/sign_up_screen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) createAccountLink.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}