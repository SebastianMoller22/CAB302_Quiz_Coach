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
 * Controller for the "Past Quizzes" view.
 * <p>
 * This controller manages the display of all quizzes previously completed by the logged-in user.
 * It loads quiz data from the {@link SessionManager}'s {@code QuizManager} and populates a JavaFX TableView.
 * The user can also return to the home page via a back button.
 */
public class PastQuizzesController implements Initializable {

    /**
     * Manages session data, including quiz history and user information.
     */
    private SessionManager sessionManager;

    /**
     * JavaFX TableView to display a list of past quizzes.
     */
    @FXML
    private TableView<Quiz> pastQuizzesTable;

    /**
     * Column displaying the unique ID of each quiz.
     */
    @FXML
    private TableColumn<Quiz, Integer> idColumn;

    /**
     * Column displaying the topic of the quiz.
     */
    @FXML
    private TableColumn<Quiz, String> topicColumn;

    /**
     * Column displaying the quiz difficulty (scale of 1–10).
     */
    @FXML
    private TableColumn<Quiz, Double> difficultyColumn;

    /**
     * Column displaying the score achieved by the user.
     */
    @FXML
    private TableColumn<Quiz, Integer> scoreColumn;

    /**
     * Button used to navigate back to the Home screen.
     */
    @FXML
    private Button backButton;

    /**
     * Observable list storing the quiz data to be displayed in the TableView.
     */
    private final ObservableList<Quiz> quizData = FXCollections.observableArrayList();

    /**
     * Initializes the controller after the FXML has been loaded.
     * Sets up cell value factories for the TableView columns and populates data.
     *
     * @param location  the location used to resolve relative paths for the root object, or {@code null}
     * @param resources the resources used to localize the root object, or {@code null}
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
     * Loads the list of past quizzes from the session's QuizManager into the TableView.
     */
    private void loadPastQuizzes() {
        if (sessionManager != null) {
            quizData.setAll(sessionManager.getQuizManager().getPastquiz());
            pastQuizzesTable.setItems(quizData);
        }
    }

    /**
     * Injects the current session into the controller.
     * This allows access to shared session data such as quiz history.
     *
     * @param session the active {@link SessionManager} instance
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
    }

    /**
     * Handles the event when the user clicks the "← Back" button.
     * Navigates back to the Home screen.
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