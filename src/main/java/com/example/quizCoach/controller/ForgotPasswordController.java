package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML private TextField usernameField;
    @FXML private Button resetButton;
    @FXML private Hyperlink existingAccountLink;
    @FXML private Hyperlink createAccountLink;

    @FXML
    public void initialize() {
        resetButton.setOnAction(e -> {
            System.out.println("Reset link sent to: " + usernameField.getText());
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