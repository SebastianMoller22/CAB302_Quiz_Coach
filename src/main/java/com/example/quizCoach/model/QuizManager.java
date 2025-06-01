package com.example.quizCoach.model;

import com.example.quizCoach.AI.Get_Sub_topics;
import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.database.SqliteQuizDAO;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages quiz generation, execution, and retrieval using AI assistance and local database.
 * Runs the quiz creation process on a separate thread.
 */
public class QuizManager{

    /**
     * The quiz currently being generated or interacted with.
     */
    private Quiz activequiz;

    /**
     * The user ID of the currently active quiz creator.
     */
    private int activequizuserid;

    /**
     * DAO object for performing quiz-related database operations.
     */
    private SqliteQuizDAO quizDatabase;

    /**
     * AI-based quiz generation engine.
     */
    Quiz_Maker quizMaker;

    /**
     * List of previously created or taken quizzes.
     */
    private List<Quiz> pastquizzes;

    /**
     * Constructs a QuizManager with initialized database and quiz history.
     */
    public QuizManager() {
        quizDatabase = new SqliteQuizDAO();
        pastquizzes = new ArrayList<Quiz>();
    }

    /**
     * Initializes the AI-based quiz generation with the specified parameters.
     *
     * @param topic     the topic of the quiz
     * @param difficulty difficulty level of the quiz (e.g., 1-5)
     * @param numMCQs   the number of multiple-choice questions to generate
     */
    public void MakeQuiz(String topic, int difficulty, int numMCQs) {
        quizMaker = new Quiz_Maker(topic, difficulty, numMCQs, 0);
    }

    /**
     * Executes the quiz creation thread.
     * Waits for the quiz to be generated, then stores it and associates it with a user.
     */
    public Task<Void> generateQuizTask(String topic, int difficulty, int numMCQs) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                quizMaker = new Quiz_Maker(topic, difficulty, numMCQs, 0);
                quizMaker.start();
                while (quizMaker.isAlive()) {
                    Thread.sleep(500); // optionally updateProgress here
                }
                activequiz = quizMaker.get_quiz();
                activequiz.setCreatedByUserId(activequizuserid);
                quizDatabase.addQuiz(activequiz);
                return null;
            }
        };
    }

    /**
     * Sets the user ID of the person generating the quiz.
     *
     * @param user_id the user ID to associate with the quiz
     */
    public void setActivequizuserid(int user_id) {this.activequizuserid = user_id;}

    /**
     * Returns the currently active quiz.
     *
     * @return the active quiz object
     */
    public Quiz getActivequiz() {return activequiz;}

    /**
     * Retrieves all quizzes created by the current user from the database and stores them locally.
     */
    public void setsPastquizzes() {
        List<Quiz> quizzlist = quizDatabase.getAllQuizzes();
        for (Quiz quiz: quizzlist) {
            if (quiz.getCreatedByUserId() == activequizuserid) {
                pastquizzes.add(quiz);
            }
        }
    }

    /**
     * Add the active quiz to the list of past quizzes and reset the active quiz.
     */
    public void return_home() {
        pastquizzes.add(activequiz);
        activequiz = null;
    }

    /**
     * Returns a list of past quizzes created by the user.
     *
     * @return a list of past quizzes
     */
    public List<Quiz> getPastquiz() { return pastquizzes; }
}
