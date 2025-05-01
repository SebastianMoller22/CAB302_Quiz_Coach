module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    exports com.example.quizCoach.AI;

    opens com.example.quizCoach.ollama to com.google.gson;
}