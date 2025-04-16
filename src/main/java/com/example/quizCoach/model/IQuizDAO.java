package com.example.quizCoach.model;

import java.util.List;

/**
 * Interface for the Quiz Data Access Object that handles
 * the CRUD operations for the Quiz class with the database.
 */
public interface IQuizDAO {
    /**
     * Adds a new Quiz to the database.
     * @param quiz The Quiz to add.
     */
    public void addQuiz(Quiz quiz);
    /**
     * Updates an existing Quiz in the database.
     * @param quiz The Quiz to update.
     */
    public void updateQuiz(Quiz quiz);
    /**
     * Deletes a Quiz from the database.
     * @param quiz The Quiz to delete.
     */
    public void deleteQuiz(Quiz quiz);
    /**
     * Retrieves a Quiz from the database.
     * @param id The id of the Quiz to retrieve.
     * @return The Quiz with the given id, or null if not found.
     */
    public Quiz getQuiz(int id);
    /**
     * Retrieves all Quizs from the database.
     * @return A list of all Quizs in the database.
     */
    public List<Quiz> getAllQuizzes();
}

