package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ResultsController {

    private AuthenticationManager authentication;

    @FXML
    private TextArea resultsTextArea;

    public void setResultsText(String text) {
        resultsTextArea.setText(text);
    }

    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setAuthManager(authentication);
            Stage stage = (Stage) resultsTextArea.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
