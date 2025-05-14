package com.example.quizCoach.model;

import com.example.quizCoach.model.QuizResults;
import java.util.List;

public interface IQuizResultsDAO {
    void addQuizResults(QuizResults result);
    QuizResults getQuizResults(int id);
    List<QuizResults> getResultsForQuiz(int quizId);
    List<QuizResults> getResultsForUser(int userId);
    void deleteQuizResults(QuizResults result);
}
