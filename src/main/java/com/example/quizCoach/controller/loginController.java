package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class loginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink forgotPasswordLink;
    @FXML private Hyperlink createAccountLink;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // TODO: Add your login validation logic
            System.out.println("Logging in: " + username);
        });

        forgotPasswordLink.setOnAction(e -> {
            System.out.println("Forgot password clicked.");
        });

        createAccountLink.setOnAction(e -> {
            System.out.println("Create account clicked.");
        });
    }
}