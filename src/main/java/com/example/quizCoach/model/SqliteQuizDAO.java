package com.example.quizCoach.model;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SqliteQuizDAO implements IQuizDAO {
    private Connection connection;

    public SqliteQuizDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create a table if no tables exist
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quizzes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "firstName VARCHAR NOT NULL,"
                    + "lastName VARCHAR NOT NULL,"
                    + "phone VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addQuiz(Quiz quiz) {

    }

    @Override
    public void updateQuiz(Quiz quiz) {

    }

    @Override
    public void deleteQuiz(Quiz quiz) {

    }

    @Override
    public Quiz getQuiz(int id) {
        return null;
    }

    @Override
    public List<Quiz> getAllQuizs() {
        return List.of();
    }
}
