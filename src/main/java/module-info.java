module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.quizCoach to javafx.fxml;
    exports com.example.quizCoach;
    exports com.example.quizCoach.controller;
    opens com.example.quizCoach.controller to javafx.fxml;
    exports com.example.quizCoach.model;
    opens com.example.quizCoach.model to javafx.fxml;
}