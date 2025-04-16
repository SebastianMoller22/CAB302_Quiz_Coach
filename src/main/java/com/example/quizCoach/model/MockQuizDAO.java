package com.example.quizCoach.model;

import java.util.ArrayList;
import java.util.List;

// FIXME This class is still full of information from MockContactDAO and needs to be changed to contain stuff for Quiz's
public class MockQuizDAO implements IQuizDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<Quiz> QUIZZES = new ArrayList<>();
    private static int autoIncrementedId = 0;

    public MockQuizDAO() {
        // Add some initial contacts to the mock database
        addQuiz(new Quiz("John", "Doe", "johndoe@example.com", "0423423423"));
        addQuiz(new Quiz("Jane", "Doe", "janedoe@example.com", "0423423424"));
        addQuiz(new Quiz("Jay", "Doe", "jaydoe@example.com", "0423423425"));

    }

    @Override
    public void addQuiz(Quiz quiz) {
        quiz.setId(autoIncrementedId);
        autoIncrementedId++;
        QUIZZES.add(quiz);
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        for (int i = 0; i < QUIZZES.size(); i++) {
            if (QUIZZES.get(i).getId() == quiz.getId()) {
                QUIZZES.set(i, quiz);
                break;
            }
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        QUIZZES.remove(quiz);
    }

    @Override
    public Quiz getQuiz(int id) {
        for (Quiz quiz : QUIZZES) {
            if (quiz.getId() == id) {
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
