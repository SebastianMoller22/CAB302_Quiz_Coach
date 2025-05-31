package com.example.quizCoach.model;

import com.example.quizCoach.AI.Get_Sub_topics;
import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.database.SqliteQuizDAO;

import java.util.Random;

public class QuizManager extends Thread{
    private Quiz activequiz;
    private SqliteQuizDAO quizDatabase;
    Quiz_Maker quizMaker;

    public QuizManager() {
        quizDatabase = new SqliteQuizDAO();
    }

    public void MakeQuiz(String topic, int difficulty, int numMCQs) {
        quizMaker = new Quiz_Maker(topic, difficulty, numMCQs, 0);

    }

    @Override
    public void run(){
        quizMaker.start();
        while (quizMaker.isAlive()){
            System.out.println("waiting");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        activequiz = quizMaker.get_quiz();
        quizDatabase.addQuiz(activequiz);

    }

    public Quiz getActivequiz() {return activequiz;}
}
