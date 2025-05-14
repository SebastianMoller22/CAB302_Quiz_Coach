package com.example.quizCoach.model;

import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.model.IQuizResultsDAO;
import com.example.quizCoach.model.SqliteQuizResultsDAO;
import com.example.quizCoach.model.QuizResults;

import java.util.List;
import java.util.Random;

public class QuizManager {
    private Quiz activequiz;
    private SqliteQuizDAO quizDatabase;
    private IQuizResultsDAO quizResultsDatabase;

    public QuizManager() {
        quizDatabase = new SqliteQuizDAO();
        quizResultsDatabase = new SqliteQuizResultsDAO();
    }

    public void MakeQuiz(String topic, int difficulty, int numMCQs) {
        Quiz_Maker quizMaker = new Quiz_Maker(topic, difficulty, numMCQs, 0);
        activequiz = quizMaker.get_quiz();
    }

    public Quiz getActivequiz() {return activequiz;}

    /** Call this after the user completes a quiz */
    public void recordResult(int userId, int quizId, int score) {
        QuizResults result = new QuizResults(quizId, userId, score);
        quizResultsDatabase.addQuizResults(result);
    }

    /** Retrieve results for a given quiz (e.g. leaderboard) */
    public List<QuizResults> getResultsForQuiz(int quizId) {
        return quizResultsDatabase.getResultsForQuiz(quizId);
    }

    /** Retrieve a user’s history of results */
    public List<QuizResults> getResultsForUser(int userId) {
        return quizResultsDatabase.getResultsForUser(userId);
    }
}
