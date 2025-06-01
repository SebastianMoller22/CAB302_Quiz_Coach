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

/**
 * Controller for the Leaderboard screen.
 * Displays a table of all past quizzes completed by users,
 * sorted by score in descending order.
 */
public class LeaderboardController implements Initializable {

    /** Manages session data including logged-in user and quiz access. */
    private SessionManager sessionManager;

    /** TableView to display leaderboard data. */
    @FXML private TableView<Quiz> leaderboardTable;

    /** TableColumn for displaying quiz IDs. */
    @FXML private TableColumn<Quiz, Integer> idColumn;

    /** TableColumn for displaying quiz topics. */
    @FXML private TableColumn<Quiz, String> topicColumn;

    /** TableColumn for displaying quiz difficulty values. */
    @FXML private TableColumn<Quiz, Double> difficultyColumn;

    /** TableColumn for displaying user scores. */
    @FXML private TableColumn<Quiz, Integer> scoreColumn;

    /** TableColumn for displaying the username of the quiz creator. */
    @FXML private TableColumn<Quiz, String> userColumn;

    /** Back button to return to the home screen. */
    @FXML private Button backButton;

    /** Data source for the leaderboard TableView. */
    private ObservableList<Quiz> leaderboardData = FXCollections.observableArrayList();

    /**
     * Initializes the leaderboard table.
     * Called automatically after the FXML layout is loaded.
     *
     * @param location  The location used to resolve relative paths for the root object, or null.
     * @param resources The resources used to localize the root object, or null.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ensure UI updates run on the JavaFX application thread
        Platform.runLater(() -> {
            // Set up column bindings
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().GetQuizID()).asObject());
            topicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().GetTopic()));
            difficultyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().GetDifficulty()).asObject());
            scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());
            userColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(sessionManager.getAuthenticationManager()
                            .getUsernameFromID(cellData.getValue().getCreatedByUserId()))
            );

            // Load quiz data and apply sorting
            loadPastQuizzes();
            leaderboardTable.getSortOrder().add(scoreColumn);
            scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        });
    }

    /**
     * Loads all quizzes from the quiz manager and populates the leaderboard table.
     */
    private void loadPastQuizzes() {
        leaderboardData.setAll(sessionManager.getQuizManager().getAllQuiz());
        leaderboardTable.setItems(leaderboardData);
    }

    /**
     * Injects the session manager after controller initialization.
     * @param session the current SessionManager instance
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /**
     * Handles navigation back to the home screen when the "← Back" button is clicked.
     * Transfers the session manager to the home controller.
     */
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