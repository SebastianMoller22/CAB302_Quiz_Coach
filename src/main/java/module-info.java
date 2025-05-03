module com.example.quizCoach {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    exports com.example.quizCoach.AI;
    exports com.example.quizCoach.authentication;
    exports com.example.quizCoach.database;
    exports com.example.quizCoach.model;

    opens com.example.quizCoach.ollama to com.google.gson;
    opens com.example.quizCoach.authentication;
    opens com.example.quizCoach.model;

    opens com.example.quizCoach to javafx.graphics;
    opens com.example.quizCoach.controller to javafx.fxml;
    exports com.example.quizCoach;
    exports com.example.quizCoach.controller;
}