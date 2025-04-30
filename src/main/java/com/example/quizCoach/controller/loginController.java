package com.example.quizCoach.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/forgot_password.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) forgotPasswordLink.getScene().getWindow();
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