package com.example.quizCoach.AI;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface Ask_Question_I {
    /**
     * Interface for asking Ollama prompts
     */

    /**
     * @param Topic
     * @param Skill_level
     * @return ObjectNode
     *
     * Prompt ollama to get a multiple chose question based in the topic and skill level, save the question, answer and options in a JSON file
     * and return the Json node to be read
     */
    public ObjectNode Multiple_Choice(String Topic, int Skill_level);

    /**
     * @param Topic
     * @param Skill_level
     * @return ObjectNode
     *
     * Prompt ollama to get a Short_Response question based in the topic and skill level, save the question in a JSON file
     * and return the Json node to be read
     */
    public ObjectNode Short_Response(String Topic, int Skill_level);

    /**
     * @param Response
     * @param Question
     * @return ObjectNode
     *
     * Prompt ollama score the response to the question, return the score and update the JSON file with the answer and score
     * and return the Json node to be read
     */
    public ObjectNode Score_Response(String Response, String Question);
}
