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

public class SignUpPage extends Application {

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

        Text welcomeText = new Text("WELCOME TO");
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setFill(Color.BLACK);

        Text intelliTutorText = new Text("IntelliTutor");
        intelliTutorText.setFont(Font.font("Comic Sans MS", 30)); // Handwritten/playful font
        intelliTutorText.setFill(Color.TEAL);

        header.getChildren().addAll(icon, welcomeText, intelliTutorText);
        mainLayout.setTop(header);

        // Create the sign-up form box
        VBox signUpBox = new VBox();
        signUpBox.setPadding(new Insets(20));
        signUpBox.setSpacing(15);
        signUpBox.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-radius: 10; -fx-background-radius: 10;");

        Text signUpTitle = new Text("SIGN UP");
        signUpTitle.setFont(Font.font("Arial", 24));
        signUpTitle.setFill(Color.BLACK);

        // Fields
        TextField firstName = new TextField();
        firstName.setPromptText("FIRST NAME");
        firstName.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        TextField lastName = new TextField();
        lastName.setPromptText("LAST NAME");
        lastName.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("USERNAME (EMAIL)");
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("PASSWORD");
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        // Sign Up button
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setStyle("-fx-background-color: teal; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10;");

        // Hyperlinks
        Hyperlink haveAnAccount = new Hyperlink("ALREADY HAVE AN ACCOUNT?");

        // Add elements to the login box
        signUpBox.getChildren().addAll(signUpTitle, firstName, lastName, usernameField, passwordField, signUpButton, haveAnAccount);

        // Center the Sign UP
        mainLayout.setCenter(signUpBox);

        // Place header in layout
        mainLayout.setTop(header); // Or use setCenter(header) if you want it vertically centered too

        // Create the scene
        Scene scene = new Scene(mainLayout, 800, 700);
        primaryStage.setTitle("Sign UpScreen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}