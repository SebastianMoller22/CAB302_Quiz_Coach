package com.example.quizCoach.model;

import java.util.ArrayList;
import java.util.List;

public class MockContactDAO implements IContactDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<Quiz> QUIZZES = new ArrayList<>();
    private static int autoIncrementedId = 0;

    public MockContactDAO() {
        // Add some initial contacts to the mock database
        addContact(new Quiz("John", "Doe", "johndoe@example.com", "0423423423"));
        addContact(new Quiz("Jane", "Doe", "janedoe@example.com", "0423423424"));
        addContact(new Quiz("Jay", "Doe", "jaydoe@example.com", "0423423425"));

    }

    @Override
    public void addContact(Quiz quiz) {
        quiz.setId(autoIncrementedId);
        autoIncrementedId++;
        QUIZZES.add(quiz);
    }

    @Override
    public void updateContact(Quiz quiz) {
        for (int i = 0; i < QUIZZES.size(); i++) {
            if (QUIZZES.get(i).getId() == quiz.getId()) {
                QUIZZES.set(i, quiz);
                break;
            }
        }
    }

    @Override
    public void deleteContact(Quiz quiz) {
        QUIZZES.remove(quiz);
    }

    @Override
    public Quiz getContact(int id) {
        for (Quiz quiz : QUIZZES) {
            if (quiz.getId() == id) {
                return quiz;
            }
        }
        return null;
    }

    @Override
    public List<Quiz> getAllContacts() {
        return new ArrayList<>(QUIZZES);
    }
}
