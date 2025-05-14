package com.example.quizCoach.model;

import com.example.quizCoach.model.QuizResults;
import java.util.List;

public interface IQuizResultsDAO {
    void addQuizResult(QuizResults result);
    QuizResults getQuizResult(int id);
    List<QuizResults> getResultsForQuiz(int quizId);
    List<QuizResults> getResultsForUser(int userId);
    void deleteQuizResult(QuizResults result);
}
