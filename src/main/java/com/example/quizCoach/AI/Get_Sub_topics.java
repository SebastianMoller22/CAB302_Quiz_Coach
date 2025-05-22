package com.example.quizCoach.AI;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;

import java.util.ArrayList;
import java.util.Arrays;

public class Get_Sub_topics{

    private ArrayList<String> Subtopics = new ArrayList<>();

    public Get_Sub_topics(String topic, int numOfSubtopics){

       /*
        Variables required to connect to the AI
         */
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        /*
        The prompt give to the AI to create the multiple choice question in a consistent formate
        Takes in the topic, and skill level to change the question topic and difficulty
         */
        String prompt = String.format("Make %d subtopics about %s, subtopics should be written in the formate T:... where the '...' is the subtopic, nothing else should be written.",numOfSubtopics, topic);

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

        this.Subtopics.addAll(Arrays.asList(Quetion));


    }

    public ArrayList<String> getSubtopics() {
        return Subtopics;
    }
}
