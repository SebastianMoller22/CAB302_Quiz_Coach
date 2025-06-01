package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.model.Question;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for PastQuizzes.fxml.
 *
 * This controller **does not** fetch or store quizzes itself.
 * Instead, an external class can call {@link #setQuizData(ObservableList)}
 * to hand it an ObservableList<Quiz> once those are loaded from wherever.
 */
public class PastQuizzesController implements Initializable {

    private SessionManager sessionManager;
    @FXML private TableView<Quiz> pastQuizzesTable;
    @FXML private TableColumn<Quiz, Integer> idColumn;
    @FXML private TableColumn<Quiz, String> topicColumn;
    @FXML private TableColumn<Quiz, Double> difficultyColumn;
    @FXML private TableColumn<Quiz, Integer> scoreColumn;
    @FXML private Button backButton;
    private ObservableList<Quiz> quizData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().GetQuizID()).asObject());
            topicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().GetTopic()));
            difficultyColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().GetDifficulty()).asObject());
            scoreColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().GetQuizID()).asObject());

            loadPastQuizzes();
        });
    }

    private void loadPastQuizzes() {
        quizData.setAll(sessionManager.getQuizManager().getPastquiz());
        pastQuizzesTable.setItems(quizData);
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