package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class ProfileController {

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();

            // ✅ Use the root to get the window, cast it properly
            Stage stage = (Stage) ((Parent) loader.getRoot()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile() {
        System.out.println("Edit Profile clicked");
        // Add your logic here
    }

    @FXML
    private void handleChangePassword() {
        System.out.println("Change Password clicked");
        // Add your logic here
    }

    @FXML
    private void handleDeleteAccount() {
        System.out.println("Delete Account clicked");
        // Add your logic here
    }
}