package com.example.quizCoach.controller;

import com.example.quizCoach.Session.SessionManager;
import com.example.quizCoach.authentication.AuthenticationManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the login screen.
 * Handles user login logic and navigation to other screens such as
 * sign-up and forgot password.
 */
public class loginController {

    /** Reference to the authentication manager handling user login. */
    @FXML private AuthenticationManager authentication;

    /** Text field for entering username. */
    @FXML private TextField usernameField;

    /** Password field for entering password securely. */
    @FXML private PasswordField passwordField;

    /** Button that triggers the login process. */
    @FXML private Button loginButton;

    /** Hyperlink that navigates to the forgot password screen. */
    @FXML private Hyperlink forgotPasswordLink;

    /** Hyperlink that navigates to the sign-up screen. */
    @FXML private Hyperlink createAccountLink;

    /** Label to show error messages during login. */
    @FXML private Label errorMessage;

    /**
     * Sets the authentication manager to be used by this controller.
     * This should be called externally to inject the current auth context.
     *
     * @param authentication the AuthenticationManager instance
     */
    public void setAuthManager(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    /**
     * Initializes the controller by attaching event listeners to UI components.
     * Handles login attempt, and navigation to sign-up or password reset screens.
     */
    @FXML
    public void initialize() {
        // Handle login attempt
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            errorMessage.setText("");
            errorMessage.setVisible(false);

            try {
                // Attempt authentication
                authentication.LoginAsUser(username, password);

                // Load the home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/home-page.fxml"));
                Parent root = loader.load();
                HomeController homeController = loader.getController();

                // Create and configure session
                SessionManager session = new SessionManager();
                session.setAuthenticationManager(authentication);
                session.getQuizManager().setActivequizuserid(session.getAuthenticationManager().getActiveUser().getId());
                session.getQuizManager().setsPastquizzes();

                // Inject session into next screen
                homeController.setSessionManager(session);

                // Navigate to home screen
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (Exception ex) {
                errorMessage.setText("Login failed: " + ex.getMessage());
                errorMessage.setVisible(true);
            }
        });

        // Navigate to forgot password screen
        forgotPasswordLink.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/forgot_password.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) forgotPasswordLink.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Navigate to sign-up screen
        createAccountLink.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizCoach/sign_up_screen.fxml"));
                Parent root = loader.load();
                SignUpController signUpController = loader.getController();
                signUpController.setAuthManager(authentication);
                Stage stage = (Stage) createAccountLink.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}