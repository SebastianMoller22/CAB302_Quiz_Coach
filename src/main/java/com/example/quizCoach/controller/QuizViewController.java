package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for displaying the active quiz in a multiple-choice format.
 * <p>
 * Responsible for rendering questions and options, capturing user responses,
 * and navigating to the results screen upon submission.
 */
public class QuizViewController {

    /** The current session which contains the active quiz and managers. */
    private SessionManager sessionManager;

    /** Map to store user answers (not actively used, but extensible). */
    private final Map<Question, String> userAnswers = new HashMap<>();

    /** The currently active quiz. */
    private Quiz quiz;

    /** VBox container for dynamically generated quiz questions. */
    @FXML
    private VBox quizContainer;

    /** Button to finalize answers and navigate to results. */
    @FXML
    private Button seeResultsButton;

    /**
     * Injects the active session and retrieves the current quiz.
     *
     * @param session the active {@link SessionManager}
     */
    public void setSessionManager(SessionManager session) {
        this.sessionManager = session;
        this.quiz = sessionManager.getQuizManager().getActivequiz();
    }

    /**
     * Initializes the quiz view by rendering each quiz question and its options.
     * This method is run on the JavaFX Application Thread.
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            for (Question question : quiz.GetQuestions()) {
                addMultipleChoiceQuestion(question);
            }
        });
    }

    /**
     * Dynamically creates and adds UI elements for a single multiple-choice question.
     *
     * @param question the {@link Question} object to display
     */
    private void addMultipleChoiceQuestion(Question question) {
        Label questionLabel = new Label(question.GetQuestionText());
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        ToggleGroup group = new ToggleGroup();
        VBox questionBox = new VBox(5);
        questionBox.getChildren().add(questionLabel);

        for (String option : question.GetOptionTexts()) {
            RadioButton rb = new RadioButton(option);
            rb.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
            rb.setToggleGroup(group);
            questionBox.getChildren().add(rb);
        }

        // Save the selected answer to the Question object
        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                question.SetSelectedOption(selected.getText());
            }
        });

        quizContainer.getChildren().add(questionBox);
    }

    /**
     * Handles user submission of the quiz and transitions to the results screen.
     * Also triggers score calculation and feedback generation.
     */
    @FXML
    private void handleSeeResults() {
        int correctCount = quiz.getScore();
        Question[] questions = quiz.GetQuestions();

        // Update score in session manager
        sessionManager.getQuizManager().UpdateScore();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/results-view.fxml"));
            Parent root = loader.load();

            ResultsController controller = loader.getController();
            String resultsText = String.format("✅ You got %d/%d correct!\n%s",
                    correctCount,
                    questions.length,
                    feedbackMessage(correctCount, questions.length));
            controller.setResultsText(resultsText);
            controller.setSessionManager(sessionManager);

            Stage stage = (Stage) seeResultsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a short feedback message based on the user's quiz score.
     *
     * @param correct number of correct answers
     * @param total   total number of questions
     * @return feedback message string
     */
    private String feedbackMessage(int correct, int total) {
        double score = (double) correct / total;
        if (score == 1.0) return "🏆 Excellent performance!";
        else if (score >= 0.7) return "👏 Good job!";
        else if (score >= 0.5) return "🙂 Not bad, keep practicing.";
        else return "😅 Needs improvement. Try again!";
    }
}