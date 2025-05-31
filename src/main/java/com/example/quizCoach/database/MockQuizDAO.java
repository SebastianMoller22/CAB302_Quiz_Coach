package com.example.quizCoach.database;

import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of IQuizDAO that stores quizzes in memory for testing purposes.
 * This class maintains an in-memory list of Quiz objects and simulates auto-incremented IDs.
 */
public class MockQuizDAO implements IQuizDAO {
    /**
     * In-memory “table” of quizzes
     */
    private static final List<Quiz> QUIZZES = new ArrayList<>();
    private static int autoIncrementedId = 1;

    /**
     * Adds a new Quiz to the in-memory list. Assigns an auto-incremented ID to the Quiz.
     * @param quiz The Quiz to add.
     */
    @Override
    public void addQuiz(Quiz quiz) {
//        quiz.setQuizID(autoIncrementedId++);
        QUIZZES.add(quiz);
    }

    /**
     * Updates an existing Quiz in the in-memory list. Finds the Quiz by its ID and replaces its data.
     * @param quiz The Quiz with updated information.
     */
    @Override
    public void updateQuiz(Quiz quiz) {
        for (int i = 0; i < QUIZZES.size(); i++) {
            if (QUIZZES.get(i).GetQuizID() == quiz.GetQuizID()) {
                QUIZZES.set(i, quiz);
                return;
            }
        }
    }

    /**
     * Deletes an existing Quiz from the in-memory list.
     * @param quiz The Quiz to delete.
     */
    @Override
    public void deleteQuiz(Quiz quiz) {
        QUIZZES.removeIf(q -> q.GetQuizID() == quiz.GetQuizID());
    }

    /**
     * Retrieves a Quiz from the in-memory list by ID.
     * @param id The ID of the Quiz to retrieve.
     * @return The Quiz with the given ID, or null if not found.
     */
    @Override
    public Quiz getQuiz(int id) {
        for (Quiz quiz : QUIZZES) {
            if (quiz.GetQuizID() == id) {
                return quiz;
            }
        }
        return null;
    }

    /**
     * Retrieves all quizzes from the in-memory list.
     * @return A list of all quizzes.
     */
    @Override
    public List<Quiz> getAllQuizzes() {
        return new ArrayList<>(QUIZZES);
    }
}
