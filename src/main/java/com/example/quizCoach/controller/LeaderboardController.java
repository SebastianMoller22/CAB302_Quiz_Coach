package com.example.quizCoach.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class LeaderboardController {

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> leaderboardListView;

    @FXML
    private BarChart<String, Number> performanceChart;

    @FXML
    public void initialize() {
        leaderboardListView.getItems().addAll(
                "Alice - 95 pts",
                "Bob - 90 pts",
                "Charlie - 88 pts",
                "Diana - 85 pts"
        );

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Test 1", 95));
        series.getData().add(new XYChart.Data<>("Test 2", 90));
        series.getData().add(new XYChart.Data<>("Test 3", 88));
        series.getData().add(new XYChart.Data<>("Test 4", 85));

        performanceChart.getData().add(series);
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}