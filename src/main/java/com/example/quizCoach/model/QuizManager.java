package com.example.quizCoach.model;

import com.example.quizCoach.AI.Get_Sub_topics;
import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.database.SqliteQuizDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizManager extends Thread{
    private Quiz activequiz;
    private int activequizuserid;
    private SqliteQuizDAO quizDatabase;
    Quiz_Maker quizMaker;
    private List<Quiz> pastquizzes;

    public QuizManager() {
        quizDatabase = new SqliteQuizDAO();
        pastquizzes = new ArrayList<Quiz>();
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
        activequiz.setCreatedByUserId(activequizuserid);
        quizDatabase.addQuiz(activequiz);
    }

    public void setActivequizuserid(int user_id) {this.activequizuserid = user_id;}

    public Quiz getActivequiz() {return activequiz;}

    public void setsPastquizzes() {
        List<Quiz> quizzlist = quizDatabase.getAllQuizzes();
        for (Quiz quiz: quizzlist) {
            if (quiz.getCreatedByUserId() == activequizuserid) {
                pastquizzes.add(quiz);
            }
        }
    }

    public void return_home() {
        pastquizzes.add(activequiz);
        activequiz = null;
    }

    public List<Quiz> getPastquiz() { return pastquizzes; }
}
