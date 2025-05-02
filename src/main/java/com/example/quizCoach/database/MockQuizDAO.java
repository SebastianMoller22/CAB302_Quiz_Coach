package com.example.quizCoach.database;

import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class MockQuizDAO implements IQuizDAO {
    /**
     * In-memory “table” of quizzes
     */
    private static final List<Quiz> QUIZZES = new ArrayList<>();
    private static int autoIncrementedId = 1;

    public MockQuizDAO() {
        // Sample Quiz 1: Basic Algebra
        Question q1 = new Question(
                "What is 2 + 2?",
                new Option[]{
                        new Option("3", false),
                        new Option("4", true)
                }
        );
        Question q2 = new Question(
                "Solve for x: 5x = 25. What is x?",
                new Option[]{
                        new Option("5", true),
                        new Option("6", false)
                }
        );
        Quiz algebraQuiz = new Quiz("Basic Algebra", 1.0f, new Question[]{q1, q2});
        addQuiz(algebraQuiz);

        // Sample Quiz 2: Multiplication Tables
        Question q3 = new Question(
                "What is 3 * 3?",
                new Option[]{
                        new Option("6", false),
                        new Option("9", true)
                }
        );
        Question q4 = new Question(
                "What is 7 * 5?",
                new Option[]{
                        new Option("35", true),
                        new Option("40", false)
                }
        );
        Quiz multiplicationQuiz = new Quiz("Multiplication Tables", 1.2f, new Question[]{q3, q4});
        addQuiz(multiplicationQuiz);
    }

    @Override
    public void addQuiz(Quiz quiz) {
        // assign a new ID and store
        quiz.SetQuizID(autoIncrementedId++);
        QUIZZES.add(quiz);
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        for (int i = 0; i < QUIZZES.size(); i++) {
            if (QUIZZES.get(i).GetQuizID() == quiz.GetQuizID()) {
                QUIZZES.set(i, quiz);
                return;
            }
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        QUIZZES.removeIf(q -> q.GetQuizID() == quiz.GetQuizID());
    }

    @Override
    public Quiz getQuiz(int id) {
        for (Quiz quiz : QUIZZES) {
            if (quiz.GetQuizID() == id) {
                return quiz;
            }
        }
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return new ArrayList<>(QUIZZES);
    }
}
