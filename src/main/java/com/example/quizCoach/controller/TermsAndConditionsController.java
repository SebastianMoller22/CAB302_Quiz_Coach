package com.example.quizCoach.controller;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.quizCoach.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

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
        String prompt = String.format("Write a multiple chose question about %s, with 4 options. The question should be scaled to a skill level of 1 where 1 is new to the topic and 100 is and expert. The question should be written in the format Q:A:O where 'Q' is the question, 'A' is the answer and 'O' are the other options, nothing else should be written.",topic);

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);

        OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

        termsAndConditions.setText(response.getResponse());

        String[] Quetion = response.getResponse().split("\n");

        for (int i = 0; i < Quetion.length; i++) {
            Quetion[i] = Quetion[i].substring(3);
        }
        System.out.println(Quetion[0]);
        System.out.println(Quetion[1]);
        System.out.println(Quetion[2]);
        System.out.println(Quetion[3]);
        System.out.println(Quetion[4]);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        ArrayNode Array = objectMapper.createArrayNode();
        jsonNode.put("Question_type", "Multiple choice");
        jsonNode.put("Question", Quetion[0]);
        jsonNode.put("Answer", Quetion[1]);

        for (int i = 2; i < Quetion.length; i++) {
            Array.add(Quetion[i]);
        }


        jsonNode.put("Other_Options", Array);

        objectMapper.writeValue(new File("src/main/java/com/example/quizCoach/JSON/mydata.json"), jsonNode);


        ArrayNode NewArray = objectMapper.createArrayNode();
        NewArray = (ArrayNode) jsonNode.get("Other_Options");


        System.out.println(NewArray.get(0).asText());
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }


}