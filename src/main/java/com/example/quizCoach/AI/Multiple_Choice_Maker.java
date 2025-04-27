package com.example.quizCoach.AI;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Multiple_Choice_Maker {

    private String Question;
    private String Answer;
    private ArrayList<String> Options = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode jsonNode = objectMapper.createObjectNode();
    private ArrayNode Array = objectMapper.createArrayNode();


    public Multiple_Choice_Maker(String topic, int skill_level){

        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        String prompt = String.format("Write a multiple Choice question about %s, with 4 options. The question should be scaled to a skill level of 1 where 1 is new to the topic and 100 is and expert. The question should be written in the format Q:A:O where 'Q' is the question, 'A' is the answer and 'O' are the other options, nothing else should be written.",topic);

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);

        OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

        String[] Quetion = response.getResponse().split("\n");


        for (int i = 0; i < Quetion.length; i++) {
            Quetion[i] = Quetion[i].substring(3);
        }
        jsonNode.put("Question", Quetion[0]);
        this.Question = Quetion[0];
        jsonNode.put("Answer", Quetion[1]);
        this.Answer = Quetion[1];


        for (int i = 2; i < Quetion.length; i++) {
            Array.add(Quetion[i]);
            this.Options.add(Quetion[i]);
        }
        jsonNode.put("Other_Options", Array);

    }

    public ObjectNode getJsonNode() {
        return jsonNode;
    }

    public String getAnswer() {
        return Answer;
    }

    public String getQuestion() {
        return Question;
    }

    public ArrayList<String> getOptions() {
        return Options;
    }
}
