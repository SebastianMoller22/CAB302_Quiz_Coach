package com.example.quizCoach.model;

import java.time.LocalDateTime;

public class QuizResults {
    private int id;
    private int quizId;
    private int userId;
    private int score;
    private LocalDateTime takenAt;

    // full-arg constructor for DAO use
    public QuizResults(int id, int quizId, int userId, int score, LocalDateTime takenAt) {
        this.id      = id;
        this.quizId  = quizId;
        this.userId  = userId;
        this.score   = score;
        this.takenAt = takenAt;
    }

    // convenience constructor for inserting new results
    public QuizResults(int quizId, int userId, int score) {
        this.id      = id;
        this.quizId  = quizId;
        this.userId  = userId;
        this.score   = score;
        this.takenAt = LocalDateTime.now();
    }

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public int getQuizId()           { return quizId; }
    public void setQuizId(int quizId){ this.quizId = quizId; }

    public int getUserId()           { return userId; }
    public void setUserId(int userId){ this.userId = userId; }

    public int getScore()            { return score; }
    public void setScore(int score)  { this.score = score; }

    public LocalDateTime getTakenAt(){ return takenAt; }
    public void setTakenAt(LocalDateTime takenAt){ this.takenAt = takenAt; }
}
