package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.model.Quiz;
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
    @FXML private TableColumn<Quiz, String> topicColumn;
    @FXML private TableColumn<Quiz, String> difficultyColumn;
    @FXML private TableColumn<Quiz, String> scoreColumn;
    @FXML private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1) Configure each column to read from the Quiz model:
        //
        //    dateColumn  ← Quiz.getDateProperty()
        //    topicColumn ← Quiz.getTopicProperty()
        //    scoreColumn ← Quiz.getScoreProperty()
        //
        // Note: Quiz must expose these as StringProperty or ObservableValue<String>.
        //dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        //topicColumn.setCellValueFactory(cellData -> cellData.getValue().topicProperty());
        //scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());

        // 2) Do NOT populate pastQuizzesTable here.  Instead, wait for setQuizData(...) to be called.
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