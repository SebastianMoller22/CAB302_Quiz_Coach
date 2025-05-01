package com.example.quizCoach.model;

import com.example.quizCoach.AI.Quiz_Maker;

import java.util.Random;

public class QuizManager {
    private Quiz activequiz;
    private SqliteQuizDAO quizDatabase;

    public QuizManager() {
        quizDatabase = new SqliteQuizDAO();
    }

    public void MakeQuiz(String topic, int difficulty, int numMCQs) {
        Quiz_Maker quizMaker = new Quiz_Maker(topic, difficulty, numMCQs, 0);
        activequiz = quizMaker.get_quiz();
    }

    public Quiz getActivequiz() {return activequiz;}
}
