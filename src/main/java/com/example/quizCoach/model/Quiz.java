package com.example.quizCoach.model;

/**
 * Represents a quiz consisting of multiple questions, a topic, and a difficulty level.
 */
public class Quiz {
    /**
     * The unique ID of the quiz.
     */
    private int quizID;

    /**
     * The topic or subject of the quiz.
     */
    private String topic;

    /**
     * The difficulty level of the quiz.
     */
    private double difficulty;

    /**
     * The array of questions included in the quiz.
     */
    private Question[] questions;

    /**
     * The ID of the user who created this quiz.
     */
    private int createdByUserId;

    /**
     * Constructs a new Quiz with the given topic, difficulty, and questions.
     *
     * @param topic      the topic of the quiz
     * @param difficulty the difficulty level of the quiz
     * @param questions  the array of questions in the quiz
     */
    public Quiz(String topic, double difficulty, Question[] questions, int createdByUserId) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questions = questions;
        this.createdByUserId = createdByUserId;
    }

    /**
     * Returns the unique ID of the quiz.
     *
     * @return the quiz ID
     */
    public int GetQuizID() {
        return quizID;
    }

    /**
     * Sets the unique ID for this quiz.
     *
     * @param quizID the ID to set
     */
    public void SetQuizID(int quizID) {
        this.quizID = quizID;
    }

    /**
     * Returns the topic of the quiz.
     *
     * @return the quiz topic
     */
    public String GetTopic() {return topic;}

    /**
     * Returns the difficulty level of the quiz.
     *
     * @return the quiz difficulty
     */
    public double GetDifficulty() {
        return difficulty;
    }

    /**
     * Returns the array of questions included in the quiz.
     *
     * @return an array of {@link Question}
     */
    public Question[] GetQuestions() {
        return questions;
    }

    /**
     * Returns the ID of the user who created the quiz.
     *
     * @return the creator's user ID
     */
    public int getCreatedByUserId() {
        return createdByUserId;
    }


    /**
     * Sets the ID of the user who created the quiz.
     *
     * @param createdByUserId the user ID to associate with this quiz
     */
    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
