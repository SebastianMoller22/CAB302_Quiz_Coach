package com.example.quizCoach;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgotPassword extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: lightblue;");

        // Create the header section
        VBox header = new VBox();
        header.setPadding(new Insets(20));
        header.setSpacing(10);
        header.setAlignment(javafx.geometry.Pos.CENTER); // ✅ This line centers the contents

        // Placeholder for graduation cap icon
        Text icon = new Text("🎓"); // Placeholder icon
        icon.setFont(Font.font(100));

        Text intelliTutorText = new Text("IntelliTutor");
        intelliTutorText.setFont(Font.font("Comic Sans MS", 30)); // Handwritten/playful font
        intelliTutorText.setFill(Color.TEAL);

        header.getChildren().addAll(icon, intelliTutorText);
        mainLayout.setTop(header);

        // Create the forgot password form box
        VBox forgotPasswordBox = new VBox();
        forgotPasswordBox.setPadding(new Insets(20));
        forgotPasswordBox.setSpacing(15);
        forgotPasswordBox.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-radius: 10; -fx-background-radius: 10;");

        Text screenTitle = new Text("FORGOT YOUR PASSWORD?");
        screenTitle.setFont(Font.font("Arial", 24));
        screenTitle.setFill(Color.BLACK);

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("ENTER YOUR USERNAME");
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        // Reset button
        Button loginButton = new Button("Email Password Reset Link");
        loginButton.setStyle("-fx-background-color: teal; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10;");

        // Hyperlinks
        Hyperlink existingAccountLink = new Hyperlink("ALREADY HAVE AN EXISTING ACCOUNT?");
        Hyperlink createAccountLink = new Hyperlink("CREATE A NEW ACCOUNT?");

        // Add elements to the login box
        forgotPasswordBox.getChildren().addAll(screenTitle, usernameField, loginButton, existingAccountLink, createAccountLink);

        // Center the login box
        mainLayout.setCenter(forgotPasswordBox);

        // Place header in layout
        mainLayout.setTop(header); // Or use setCenter(header) if you want it vertically centered too

        // Create the scene
        Scene scene = new Scene(mainLayout, 800, 700);
        primaryStage.setTitle("Forgot Password");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}