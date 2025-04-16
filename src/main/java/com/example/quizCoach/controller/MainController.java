package com.example.quizCoach.controller;

import com.example.quizCoach.model.Quiz;
import com.example.quizCoach.model.IQuizDAO;
import com.example.quizCoach.model.MockQuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;

public class MainController {
    @FXML
    private ListView<Quiz> quizzesListView;
    private IQuizDAO quizDAO;
    public MainController() {
        quizDAO = new MockQuizDAO();
        quizDAO.addQuiz(new Quiz("Jerry", "Doe", "jerrydoe@example.com", "0423423426"));
    }

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private VBox quizContainer;

    /**
     * Programmatically selects a quiz in the list view and
     * updates the text fields with the quiz's information.
     * @param quiz The quiz to select.
     */
    private void selectQuiz(Quiz quiz) {
        quizzesListView.getSelectionModel().select(quiz);
        firstNameTextField.setText(quiz.getFirstName());
        lastNameTextField.setText(quiz.getLastName());
        emailTextField.setText(quiz.getEmail());
        phoneTextField.setText(quiz.getPhone());
    }

    /**
     * Renders a cell in the quizs list view by setting the text to the quiz's full name.
     * @param quizListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Quiz> renderCell(ListView<Quiz> quizListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a quiz is selected in the list view.
             *
             * @param mouseEvent The event to handle.
             */
            private void onQuizSelected(MouseEvent mouseEvent) {
                ListCell<Quiz> clickedCell = (ListCell<Quiz>) mouseEvent.getSource();
                // Get the selected quiz from the list view
                Quiz selectedQuiz = clickedCell.getItem();
                if (selectedQuiz != null) selectQuiz(selectedQuiz);
            }

            /**
             * Updates the item in the cell by setting the text to the quiz's full name.
             *
             * @param quiz The quiz to update the cell with.
             * @param empty   Whether the cell is empty.
             */
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                // If the cell is empty, set the text to null, otherwise set it to the quiz's full name
                if (empty || quiz == null || quiz.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onQuizSelected);
                } else {
                    setText(quiz.getFullName());
                }
            }
        };
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected quiz from the list view
        Quiz selectedQuiz = quizzesListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            selectedQuiz.setFirstName(firstNameTextField.getText());
            selectedQuiz.setLastName(lastNameTextField.getText());
            selectedQuiz.setEmail(emailTextField.getText());
            selectedQuiz.setPhone(phoneTextField.getText());
            quizDAO.updateQuiz(selectedQuiz);
            syncQuizzes();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected quiz from the list view
        Quiz selectedQuiz = quizzesListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            quizDAO.deleteQuiz(selectedQuiz);
            syncQuizzes();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new quiz
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Quiz";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        Quiz newQuiz = new Quiz(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE);
        // Add the new quiz to the database
        quizDAO.addQuiz(newQuiz);
        syncQuizzes();
        // Select the new quiz in the list view
        // and focus the first name text field
        selectQuiz(newQuiz);
        firstNameTextField.requestFocus();
    }

    @FXML
    private void onCancel() {
        // Find the selected quiz
        Quiz selectedQuiz = quizzesListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            // Since the quiz hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectQuiz(selectedQuiz);
        }
    }

    /**
     * Synchronizes the quizs list view with the quizs in the database.
     */
    private void syncQuizzes() {
        Quiz currentQuiz = quizzesListView.getSelectionModel().getSelectedItem();
        quizzesListView.getItems().clear();
        List<Quiz> quizzes = quizDAO.getAllQuizs();
        boolean hasQuiz = !quizzes.isEmpty();
        if (hasQuiz) {
            quizzesListView.getItems().addAll(quizzes);
            Quiz nextQuiz = quizzes.contains(currentQuiz) ? currentQuiz : quizzes.get(0);
            quizzesListView.getSelectionModel().select(nextQuiz);
            selectQuiz(nextQuiz);
        }
        // Show / hide based on whether there are quizs
        quizContainer.setVisible(hasQuiz);
    }

    @FXML
    public void initialize() {
        quizzesListView.setCellFactory(this::renderCell);
        syncQuizzes();
        // Select the first quiz and display its information
        quizzesListView.getSelectionModel().selectFirst();
        Quiz firstQuiz = quizzesListView.getSelectionModel().getSelectedItem();
        if (firstQuiz != null) {
            selectQuiz(firstQuiz);
        }
    }
}

