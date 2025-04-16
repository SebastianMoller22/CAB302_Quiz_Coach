module com.example.addressbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quizCoach to javafx.fxml;
    exports com.example.quizCoach;
}