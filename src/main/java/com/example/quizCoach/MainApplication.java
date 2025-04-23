package com.example.quizCoach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/* hello */

public class MainApplication extends Application {
    public static final String TITLE = "Quiz Coach";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
//        stage.setTitle(TITLE);
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        // Launch the LoginScreen instead of the hello-view.fxml
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}