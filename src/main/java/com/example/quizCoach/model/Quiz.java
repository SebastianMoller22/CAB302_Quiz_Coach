package com.example.quizCoach.model;

public class Quiz {
    private int quizID;
    private String topic;
    private float difficulty;
    private Question[] questions;
    private int allocatedTime;

    public Quiz(float difficulty, Question[] questions, int allocatedTime) {
        this.difficulty = difficulty;
        this.questions = questions;
        this.allocatedTime = allocatedTime;
    }

    public int GetQuizID() {
        return quizID;
    }

    public void SetQuizID(int quizID) {
        this.quizID = quizID;
    }

    public float GetDifficulty() {
        return difficulty;
    }

    public Question[] GetQuestions() {
        return questions;
    }

    public int GetAllocatedTime() {
        return allocatedTime;
    }
}
