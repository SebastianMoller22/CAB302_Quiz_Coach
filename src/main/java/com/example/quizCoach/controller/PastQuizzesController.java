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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the "Past Quizzes" screen.
 *
 * Displays a table of previously taken quizzes for the logged-in user.
 * Quiz data is retrieved from the {@link SessionManager}'s QuizManager.
 */
public class PastQuizzesController implements Initializable {

    /** Active session containing the user and quiz managers. */
    private SessionManager sessionManager;

    /** TableView displaying the list of past quizzes. */
    @FXML private TableView<Quiz> pastQuizzesTable;

    /** Table column for Quiz ID. */
    @FXML private TableColumn<Quiz, Integer> idColumn;

    /** Table column for Quiz topic. */
    @FXML private TableColumn<Quiz, String> topicColumn;

    /** Table column for Quiz difficulty. */
    @FXML private TableColumn<Quiz, Double> difficultyColumn;

    /** Table column for user's score. */
    @FXML private TableColumn<Quiz, Integer> scoreColumn;

    /** Button to return to the home screen. */
    @FXML private Button backButton;

    /** Observable list of quizzes shown in the table. */
    private final ObservableList<Quiz> quizData = FXCollections.observableArrayList();

    /**
     * Initializes the controller and binds table columns to Quiz properties.
     * Runs on the JavaFX application thread after all @FXML fields are injected.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            idColumn.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().GetQuizID()).asObject());
            topicColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().GetTopic()));
            difficultyColumn.setCellValueFactory(cellData ->
                    new SimpleDoubleProperty(cellData.getValue().GetDifficulty()).asObject());
            scoreColumn.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());

            loadPastQuizzes();
        });
    }

    /**
     * Loads quiz history from the session's QuizManager and displays it.
     */
    private void loadPastQuizzes() {
        if (sessionManager != null) {
            quizData.setAll(sessionManager.getQuizManager().getPastquiz());
            pastQuizzesTable.setItems(quizData);
        }
    }

    /**
     * Injects the session manager used throughout the app.
     *
     * @param session the active session manager
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /**
     * Handles the "Back" button press.
     * Returns the user to the Home screen.
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