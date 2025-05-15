package com.example.quizCoach.AI;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Short_respons_maker {

    /*
    The following are variables related to the multiple choice question
    --------------------------------------------------------------------------------------------------------------------
    Question: The question created by the AI
    Answer: The answer to the Question
    Options: list of Alternative WRONG options to the Question
     */
    private String Question;
    private int Score = 0;

    /*
    The following are Json related to the multiple choice question
    --------------------------------------------------------------------------------------------------------------------
    objectMapper: Object mapper that helps create all the following variables
    jsonNode: The Json file node that will be used to store and create all the Json file with all the previous variables
    Array: An array node of the alternative WORDING option, the same as Options
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode jsonNode = objectMapper.createObjectNode();
    private ArrayNode Array = objectMapper.createArrayNode();


    public Short_respons_maker(String topic, String Subtopic, double skill_level){

        /*
        Variables required to connect to the AI
         */
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        /*
        The prompt give to the AI to create the multiple choice question in a consistent formate
        Takes in the topic, and skill level to change the question topic and difficulty
         */
        String prompt = String.format("Write a short response question about %s, with the subtopic being %s. The question should be written in the format Q:... where '...' is the question, nothing else should be written. The question difficulty should be based on the number %f, with 1 being easy and 10 being extremely difficult.",topic,Subtopic ,skill_level);

        /*
        Collects AI answer
         */
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

        /*
        Serrated the AI answer by the end of line
         */
        String Quetion = response.getResponse();

        /*
        Removes the first 3 characters form each line
         */
        Quetion = Quetion.substring(3);

        /*
        Stores the Question and answer into the variables and jons node
         */
        jsonNode.put("Question", Quetion);
        this.Question = Quetion;


    }

    /*
    THIS IS A TEST CONSTRUCTOR
    ONLY TO BE CALLED DURING TESTING
     */
    public Short_respons_maker(boolean Testvar){


    }

    /*
    When prompted gets the json node
     */
    public ObjectNode getJsonNode() {
        return jsonNode;
    }

    /*
    When prompted gets the Question
     */
    public String getQuestion() {
        return Question;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score){
        this.Score = score;
    }
}
