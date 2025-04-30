package com.example.quizCoach.model;

public class Quiz {
    private int quizID;
    private float difficulty;
    private Question[] questions;
    private int allocatedTime;

    public Quiz(int quizID, float difficulty, Question[] questions, int allocatedTime) {
        this.quizID = quizID;
        this.difficulty = difficulty;
        this.questions = questions;
        this.allocatedTime = allocatedTime;
    }

    public int GetQuizID() {
        return quizID;
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
