package com.example.quizCoach.controller;

import com.example.quizCoach.model.Quiz;
import com.example.quizCoach.model.IContactDAO;
import com.example.quizCoach.model.MockContactDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;

public class MainController {
    @FXML
    private ListView<Quiz> contactsListView;
    private IContactDAO contactDAO;
    public MainController() {
        contactDAO = new MockContactDAO();
        contactDAO.addContact(new Quiz("Jerry", "Doe", "jerrydoe@example.com", "0423423426"));
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
    private VBox contactContainer;

    /**
     * Programmatically selects a contact in the list view and
     * updates the text fields with the contact's information.
     * @param quiz The contact to select.
     */
    private void selectContact(Quiz quiz) {
        contactsListView.getSelectionModel().select(quiz);
        firstNameTextField.setText(quiz.getFirstName());
        lastNameTextField.setText(quiz.getLastName());
        emailTextField.setText(quiz.getEmail());
        phoneTextField.setText(quiz.getPhone());
    }

    /**
     * Renders a cell in the contacts list view by setting the text to the contact's full name.
     * @param contactListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Quiz> renderCell(ListView<Quiz> contactListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a contact is selected in the list view.
             *
             * @param mouseEvent The event to handle.
             */
            private void onContactSelected(MouseEvent mouseEvent) {
                ListCell<Quiz> clickedCell = (ListCell<Quiz>) mouseEvent.getSource();
                // Get the selected contact from the list view
                Quiz selectedQuiz = clickedCell.getItem();
                if (selectedQuiz != null) selectContact(selectedQuiz);
            }

            /**
             * Updates the item in the cell by setting the text to the contact's full name.
             *
             * @param quiz The contact to update the cell with.
             * @param empty   Whether the cell is empty.
             */
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || quiz == null || quiz.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onContactSelected);
                } else {
                    setText(quiz.getFullName());
                }
            }
        };
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected contact from the list view
        Quiz selectedQuiz = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            selectedQuiz.setFirstName(firstNameTextField.getText());
            selectedQuiz.setLastName(lastNameTextField.getText());
            selectedQuiz.setEmail(emailTextField.getText());
            selectedQuiz.setPhone(phoneTextField.getText());
            contactDAO.updateContact(selectedQuiz);
            syncContacts();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected contact from the list view
        Quiz selectedQuiz = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            contactDAO.deleteContact(selectedQuiz);
            syncContacts();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new contact
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Contact";
        final String DEFAULT_EMAIL = "";
        final String DEFAULT_PHONE = "";
        Quiz newQuiz = new Quiz(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE);
        // Add the new contact to the database
        contactDAO.addContact(newQuiz);
        syncContacts();
        // Select the new contact in the list view
        // and focus the first name text field
        selectContact(newQuiz);
        firstNameTextField.requestFocus();
    }

    @FXML
    private void onCancel() {
        // Find the selected contact
        Quiz selectedQuiz = contactsListView.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            // Since the contact hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectContact(selectedQuiz);
        }
    }

    /**
     * Synchronizes the contacts list view with the contacts in the database.
     */
    private void syncContacts() {
        Quiz currentQuiz = contactsListView.getSelectionModel().getSelectedItem();
        contactsListView.getItems().clear();
        List<Quiz> quizzes = contactDAO.getAllContacts();
        boolean hasContact = !quizzes.isEmpty();
        if (hasContact) {
            contactsListView.getItems().addAll(quizzes);
            Quiz nextQuiz = quizzes.contains(currentQuiz) ? currentQuiz : quizzes.get(0);
            contactsListView.getSelectionModel().select(nextQuiz);
            selectContact(nextQuiz);
        }
        // Show / hide based on whether there are contacts
        contactContainer.setVisible(hasContact);
    }

    @FXML
    public void initialize() {
        contactsListView.setCellFactory(this::renderCell);
        syncContacts();
        // Select the first contact and display its information
        contactsListView.getSelectionModel().selectFirst();
        Quiz firstQuiz = contactsListView.getSelectionModel().getSelectedItem();
        if (firstQuiz != null) {
            selectContact(firstQuiz);
        }
    }
}

