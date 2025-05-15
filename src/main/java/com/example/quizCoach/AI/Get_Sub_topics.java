package com.example.quizCoach.AI;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;

import java.util.ArrayList;

public class Get_Sub_topics extends Thread{

    private ArrayList<String> Subtopics = new ArrayList<>();
    private String topic;
    private double NumOfSubtopics;

    public Get_Sub_topics(String topic, int numOfSubtopics){
        this.topic = topic;
        this.NumOfSubtopics = numOfSubtopics;

        /*
        Variables required to connect to the AI
         */
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        /*
        The prompt give to the AI to create the multiple choice question in a consistent formate
        Takes in the topic, and skill level to change the question topic and difficulty
         */
        String prompt = String.format("Make %f subtopics about %s, subtopics should be written in the formate T:... where the '...' is the subtopic, nothing else should be written.",NumOfSubtopics, topic);

        /*
        Collects AI answer
         */
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

        /*
        Serrated the AI answer by the end of line
         */
        String[] Quetion = response.getResponse().split("\n");

        /*
        Removes the first 3 characters form each line
         */
        for (int i = 0; i < Quetion.length; i++) {
            Quetion[i] = Quetion[i].substring(3);
        }

        for (int i = 0; i < Quetion.length; i++) {
            this.Subtopics.add(Quetion[i]);
        }


    }

    public ArrayList<String> getSubtopics() {
        return Subtopics;
    }
}
