package com.example.quizCoach.model;

import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.database.SqliteQuizDAO;

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
        quizDatabase.addQuiz(activequiz);
    }

    public Quiz getActivequiz() {return activequiz;}
}
