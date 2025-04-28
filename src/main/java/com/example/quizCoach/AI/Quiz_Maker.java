package com.example.quizCoach.AI;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Quiz_Maker {

    /*
    The following are variables related to the quiz
    --------------------------------------------------------------------------------------------------------------------
    Topic: Is the inputted 'topic' that the AI will create the question about
    Skill_level: Is the inputted 'skill level' that teh question the AI creates will scale the difficulty too
    NumMultipleChoice: The inputted number of multiple choice questions to be created
    NumShortResponse: The inputted number of short response questions to be created
    MultipleChoiceArray: A created list of multiple chose question that can be easily accessed
     */
    private String Topic;
    private int Skill_level;
    private int NumMultipleChoice;
    private int NumShortResponse;

    private ArrayList <Multiple_Choice_Maker> MultipleChoiceArray = new ArrayList<>();

    /*
    The following is the Json related variables
    --------------------------------------------------------------------------------------------------------------------
    objectMapper: Object mapper that helps create all the following variables
    JsonNode: The Json file node that will be used to store and create all the Json file with all the previous varables
    MultipleChoiceList: An array node of the multiple choice questions, the same as MultipleChoiceArray
    ShortResponse: An array node of the short response questions, the same as (yet to be made)
     */

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode JsonNode = objectMapper.createObjectNode();
    private ArrayNode MultipleChoiceList = objectMapper.createArrayNode();
    private ArrayNode ShortResponse = objectMapper.createArrayNode();


    public Quiz_Maker(String topic, int skill_level, int numMultipleChoice, int numShortResponse){
        /*
        Store all inputs into their respective variables
         */
        this.Topic = topic;
        this.Skill_level = skill_level;
        this.NumMultipleChoice = numMultipleChoice;
        this.NumShortResponse = numShortResponse;

        /*
        Store the updated variables into the JsonNode
        And create the questions and store them in Json node
         */
        this.JsonNode.put("Topic", Topic);
        JsonNode.put("Number of MultipleChoice", NumMultipleChoice);
        JsonNode.put("Multiple Choice Questions", MakeMutipleChoice(Topic, Skill_level, NumMultipleChoice));
        JsonNode.put("Number of Short Response", NumShortResponse);
        JsonNode.put("Short Response Questions", MakeShortResponse(Topic, Skill_level, NumShortResponse));

    }

    private ArrayNode MakeMutipleChoice(String topic, int skill_level, int numMultipleChoice){

        /*
        Prompted the AI to crete a set number of multiple choice questions and store
        them in MultipleChoiceArray and MultipleChoiceList.
        Return MultipleChoiceList
         */
        for (int j = 0; j < numMultipleChoice; j++) {

            Multiple_Choice_Maker Question = new Multiple_Choice_Maker(Topic, Skill_level);
            MultipleChoiceArray.add(Question);
            MultipleChoiceList.add(Question.getJsonNode());
        }

        return MultipleChoiceList;
    }


    private ArrayNode MakeShortResponse(String topic, int skill_level, int numShortResponse){
        /*
        (yet to be made)
         */
        return ShortResponse;
    }

    /*
    Get MultipleChoiceArray, so other classes can access the multiple choice questions
     */
    public ArrayList<Multiple_Choice_Maker> getMultipleChoiceArray() {
        return MultipleChoiceArray;
    }


    /*
    create a Json file from the Json node and store it into the JSON folder for it to be uploaded
     */
    public void UploadJSON(){
        try{
            objectMapper.writeValue(new File("src/main/java/com/example/quizCoach/JSON/Quiz.json"), JsonNode);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
