package com.example.quizCoach.AI;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Quiz_Maker {

    private String Topic;
    private int Skill_level;
    private int NumMultipleChoice;
    private int NumShortResponse;

    private ArrayList <Multiple_Choice_Maker> MultipleChoiceArray = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode JsonNode = objectMapper.createObjectNode();
    private ArrayNode MultipleChoiceList = objectMapper.createArrayNode();
    private ArrayNode ShortResponse = objectMapper.createArrayNode();


    public Quiz_Maker(String topic, int skill_level, int numMultipleChoice, int numShortResponse){
        this.Topic = topic;
        this.Skill_level = skill_level;
        this.NumMultipleChoice = numMultipleChoice;
        this.NumShortResponse = numShortResponse;

        this.JsonNode.put("Topic", Topic);
        JsonNode.put("Number of MultipleChoice", NumMultipleChoice);
        JsonNode.put("Multiple Choice Questions", MakeMutipleChoice(Topic, Skill_level, NumMultipleChoice));
        JsonNode.put("Number of Short Response", NumShortResponse);
        JsonNode.put("Short Response Questions", MakeShortResponse(Topic, Skill_level, NumShortResponse));

    }

    private ArrayNode MakeMutipleChoice(String topic, int skill_level, int numMultipleChoice){


        for (int j = 0; j < numMultipleChoice; j++) {

            Multiple_Choice_Maker Question = new Multiple_Choice_Maker(Topic, Skill_level);
            MultipleChoiceArray.add(Question);
            MultipleChoiceList.add(Question.getJsonNode());
        }

        return MultipleChoiceList;
    }


    private ArrayNode MakeShortResponse(String topic, int skill_level, int numShortResponse){

        return ShortResponse;
    }

    public ArrayList<Multiple_Choice_Maker> getMultipleChoiceArray() {
        return MultipleChoiceArray;
    }

    public void UploadJSON(){
        try{
            objectMapper.writeValue(new File("src/main/java/com/example/quizCoach/JSON/Quiz.json"), JsonNode);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
