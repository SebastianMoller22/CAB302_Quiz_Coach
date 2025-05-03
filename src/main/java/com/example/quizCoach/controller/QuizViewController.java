package com.example.quizCoach.controller;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;
import com.example.quizCoach.model.QuizManager;
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
import java.util.List;
import java.util.Map;

public class QuizViewController {

    private AuthenticationManager authentication;

    private final Map<Question, String> userAnswers = new HashMap<>();

    private Quiz quiz;

    @FXML
    private VBox quizContainer;

    @FXML
    private Button seeResultsButton;

    public void setQuizManager(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @FXML
    public void initialize() {
        // Sample questions (could be AI-generated later)
        Platform.runLater(() -> {
            for (Question question: quiz.GetQuestions()) {
                addMultipleChoiceQuestion(question);
            }
        });

    }

    private void addMultipleChoiceQuestion(Question question) {
        Label questionLabel = new Label(question.GetQuestionText());
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        ToggleGroup group = new ToggleGroup();
        VBox questionBox = new VBox(5);
        questionBox.getChildren().add(questionLabel);

        for (String option : question.GetOptionTexts()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(group);
            rb.setStyle("-fx-text-fill: white;");
            questionBox.getChildren().add(rb);
        }

        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selected = (RadioButton) newToggle;
                userAnswers.put(question, selected.getText());
            }
        });

        quizContainer.getChildren().add(questionBox);
    }

    @FXML
    private void handleSeeResults() {
        int correctCount = 0;
        Question[] questions = quiz.GetQuestions();

        for (Question question : questions) {
            String userAnswer = userAnswers.get(question);
            String correctAnswer = question.GetCorrectOptionText();
            if (correctAnswer.equals(userAnswer)) {
                correctCount++;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/results-view.fxml"));
            Parent root = loader.load();

            ResultsController controller = loader.getController();
            String resultsText = String.format("✅ You got %d/%d correct!\n%s",
                    correctCount,
                    questions.length,
                    feedbackMessage(correctCount, questions.length));
            controller.setResultsText(resultsText);
            controller.setAuthManager(authentication);

            Stage stage = (Stage) seeResultsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String feedbackMessage(int correct, int total) {
        double score = (double) correct / total;
        if (score == 1.0) return "🏆 Excellent performance!";
        else if (score >= 0.7) return "👏 Good job!";
        else if (score >= 0.5) return "🙂 Not bad, keep practicing.";
        else return "😅 Needs improvement. Try again!";
    }
}
