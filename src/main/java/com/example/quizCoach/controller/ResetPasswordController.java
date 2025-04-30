package com.example.quizCoach.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Button resetPasswordButton;

    @FXML
    public void initialize() {
        resetPasswordButton.setOnAction(event -> {
            // Step 1: Print new password to console
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            System.out.println("New Password: " + newPassword);
            System.out.println("Confirm Password: " + confirmPassword);

            // Step 2: Switch back to login screen
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