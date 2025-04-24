package com.example.quizCoach.controller;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;

import com.example.quizCoach.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TermsAndConditionsController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextArea termsAndConditions;
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;
    @FXML
    private TextField Topic_name;


    @FXML
    public void initialize() {
        termsAndConditions.setText("Ai ouput");
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("AI test Application");
    }

    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }

    @FXML
    public void onNextButtonClick() throws IOException {
        String topic = Topic_name.getText();
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        String prompt = String.format("Ask me a question about %s, in the formate 'Q:A' with Q being the question and A being the answer",topic);

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);

        OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

        System.out.println("======================================================");
        System.out.print("You asked: ");
        System.out.println(prompt);
        System.out.println("======================================================");
        System.out.print("Ollama says: ");
        System.out.println(response.getResponse());
        System.out.println("======================================================");

        termsAndConditions.setText(response.getResponse());

    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }


}