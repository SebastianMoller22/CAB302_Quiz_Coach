package com.example.quizCoach.AI;

import com.example.quizCoach.ollama.OllamaResponse;
import com.example.quizCoach.ollama.OllamaResponseFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Multiple_Choice_Maker {

    /*
    The following are variables related to the multiple choice question
    --------------------------------------------------------------------------------------------------------------------
    Question: The question created by the AI
    Answer: The answer to the Question
    Options: list of Alternative WRONG options to the Question
     */
    private String Question;
    private String Answer;
    private ArrayList<String> Options = new ArrayList<>();
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


    public Multiple_Choice_Maker(String topic, String Subtopic, double skill_level){

        /*
        Variables required to connect to the AI
         */
        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "gemma3:12b";
        /*
        The prompt give to the AI to create the multiple choice question in a consistent formate
        Takes in the topic, and skill level to change the question topic and difficulty
         */
        String prompt = String.format("Write a multiple Choice question about %s, with the subtopic being %s, with 4 options. The question should be written in the format Q:A:O where 'Q' is the question, 'A' is the answer and 'O' are the other options, nothing else should be written. The question difficulty should be based on the number %f, with 1 being easy and 10 being extremely difficult.",topic,Subtopic ,skill_level);

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

        /*
        Stores the Question and answer into the variables and jons node
         */
        jsonNode.put("Question", Quetion[0]);
        this.Question = Quetion[0];
        jsonNode.put("Answer", Quetion[1]);
        this.Answer = Quetion[1];

        /*
        Stores the Alterative WORDING options into Options and array node
         */
        for (int i = 2; i < Quetion.length; i++) {
            Array.add(Quetion[i]);
            this.Options.add(Quetion[i]);
        }
        /*
        Added the Array node to the json node
         */
        jsonNode.put("Other_Options", Array);

    }

    /*
    THIS IS A TEST CONSTRUCTOR
    ONLY TO BE CALLED DURING TESTING
     */
    public Multiple_Choice_Maker(boolean Testvar){

        /*
        Make an AI prompt to be used for testing
         */

        String TestAI = "Q: Which of the following is a primary reason sharks are considered apex predators in most marine ecosystems?\n" +
                "A: They have few natural predators themselves.\n" +
                "O: They primarily feed on algae and seaweed.\n" +
                "O: They are typically smaller than most fish species.\n" +
                "O: They rely on symbiotic relationships with other marine animals for survival.";

        /*
        Serrated the AI answer by the end of line
         */
        String[] Quetion = TestAI.split("\n");

        /*
        Removes the first 3 characters form each line
         */
        for (int i = 0; i < Quetion.length; i++) {
            Quetion[i] = Quetion[i].substring(3);
        }

        /*
        Stores the Question and answer into the variables and jons node
         */
        jsonNode.put("Question", Quetion[0]);
        this.Question = Quetion[0];
        jsonNode.put("Answer", Quetion[1]);
        this.Answer = Quetion[1];

        /*
        Stores the Alterative WORDING options into Options and array node
         */
        for (int i = 2; i < Quetion.length; i++) {
            Array.add(Quetion[i]);
            this.Options.add(Quetion[i]);
        }
        /*
        Added the Array node to the json node
         */
        jsonNode.put("Other_Options", Array);
    }

    /*
    When prompted gets the json node
     */
    public ObjectNode getJsonNode() {
        return jsonNode;
    }

    /*
    When prompted gets the Answer
     */
    public String getAnswer() {
        return Answer;
    }

    /*
    When prompted gets the Question
     */
    public String getQuestion() {
        return Question;
    }

    /*
    When prompted gets the Options list
     */
    public ArrayList<String> getOptions() {
        return Options;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score){
        this.Score = score;
    }
}
