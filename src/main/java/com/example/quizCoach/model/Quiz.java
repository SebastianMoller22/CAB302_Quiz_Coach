package com.example.quizCoach.model;

public class Quiz {
    private int quizID;
    private String topic;
    private float difficulty;
    private Question[] questions;

    public Quiz(String topic, float difficulty, Question[] questions) {
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

    public float GetDifficulty() {
        return difficulty;
    }

    public Question[] GetQuestions() {
        return questions;
    }
}
