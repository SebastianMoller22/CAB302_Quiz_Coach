package com.example.quizCoach;

import com.example.quizCoach.controller.loginController;
import com.example.quizCoach.model.AuthenticationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication extends Application {
    AuthenticationManager authentication = new AuthenticationManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/quizCoach/login_screen.fxml")));
        Parent root = loader.load();
        primaryStage.setTitle("Login Screen");
        loginController LoginController = loader.getController();
        LoginController.setAuthManager(new AuthenticationManager());
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}