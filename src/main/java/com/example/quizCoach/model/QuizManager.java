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

    private void shuffleQuestion(Option[] options)
    {
        Random rnd = new Random();
        for (int i = options.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Option a = options[index];
            options[index] = options[i];
            options[i] = a;
        }
    }
}
