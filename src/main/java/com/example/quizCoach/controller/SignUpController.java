package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink haveAccountLink;

    @FXML
    public void initialize() {
        signUpButton.setOnAction(e -> {
            System.out.println("Signed up as: " + usernameField.getText());
        });

        haveAccountLink.setOnAction(e -> {
            // Navigate back to login (if needed)
        });
    }
}