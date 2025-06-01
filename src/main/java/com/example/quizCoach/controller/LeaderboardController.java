package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.model.Quiz;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    private SessionManager sessionManager;
    @FXML private TableView<Quiz> leaderboardTable;
    @FXML private TableColumn<Quiz, Integer> idColumn;
    @FXML private TableColumn<Quiz, String> topicColumn;
    @FXML private TableColumn<Quiz, Double> difficultyColumn;
    @FXML private TableColumn<Quiz, Integer> scoreColumn;
    @FXML private TableColumn<Quiz, String> userColumn;
    @FXML private Button backButton;
    private ObservableList<Quiz> leaderboardData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().GetQuizID()).asObject());
            topicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().GetTopic()));
            difficultyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().GetDifficulty()).asObject());
            scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
            userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(sessionManager.getAuthenticationManager().getUsernameFromID(cellData.getValue().getCreatedByUserId())));
            loadPastQuizzes();

            leaderboardTable.getSortOrder().add(scoreColumn);
            scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        });
    }

    private void loadPastQuizzes() {
        leaderboardData.setAll(sessionManager.getQuizManager().getAllQuiz());
        leaderboardTable.setItems(leaderboardData);
    }

    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /** Called when the user clicks “← Back” in the top HBox. */
    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setSessionManager(sessionManager);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}