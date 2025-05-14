package com.example.quizCoach.model;

public class Quiz {
    private int quizID;
    private String topic;
    private double difficulty;
    private Question[] questions;
    private int createdByUserId;

    public Quiz(String topic, double difficulty, Question[] questions) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public int GetQuizID() {
        return quizID;
    }

    public void SetQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String GetTopic() {return topic;}

    public double GetDifficulty() {
        return difficulty;
    }

    public Question[] GetQuestions() {
        return questions;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
