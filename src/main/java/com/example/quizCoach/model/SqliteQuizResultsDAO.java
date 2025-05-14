package com.example.quizCoach.model;

import com.example.quizCoach.model.QuizResults;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SqliteQuizResultsDAO implements IQuizResultsDAO {
    private final Connection connection;

    public SqliteQuizResultsDAO() {
        connection = SqliteConnection.getInstance();
        enableForeignKeys();
        createTable();
    }

    private void enableForeignKeys() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String ddl = """
            CREATE TABLE IF NOT EXISTS quiz_results (
              id        INTEGER PRIMARY KEY AUTOINCREMENT,
              quiz_id   INTEGER NOT NULL,
              user_id   INTEGER NOT NULL,
              score     INTEGER NOT NULL,
              taken_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
              FOREIGN KEY(quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
              FOREIGN KEY(user_id) REFERENCES users(id)   ON DELETE CASCADE
            );
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(ddl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addQuizResults(QuizResults result) {
        String sql = """
            INSERT INTO quiz_results (quiz_id, user_id, score, taken_at)
            VALUES (?, ?, ?, ?);
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, result.getQuizId());
            ps.setInt(2, result.getUserId());
            ps.setInt(3, result.getScore());
            ps.setString(4, result.getTakenAt().toString());  // SQLite will parse ISO datetime
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    result.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QuizResults getQuizResults(int id) {
        String sql = "SELECT quiz_id, user_id, score, taken_at FROM quiz_results WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDateTime takenAt = LocalDateTime.parse(rs.getString("taken_at"));
                    return new QuizResults(
                            id,
                            rs.getInt("quiz_id"),
                            rs.getInt("user_id"),
                            rs.getInt("score"),
                            takenAt
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<QuizResults> getResultsForQuiz(int quizId) {
        String sql = "SELECT id, user_id, score, taken_at FROM quiz_results WHERE quiz_id = ?;";
        List<QuizResults> results = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new QuizResults(
                            rs.getInt("id"),
                            quizId,
                            rs.getInt("user_id"),
                            rs.getInt("score"),
                            LocalDateTime.parse(rs.getString("taken_at"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<QuizResults> getResultsForUser(int userId) {
        String sql = "SELECT id, quiz_id, score, taken_at FROM quiz_results WHERE user_id = ?;";
        List<QuizResults> results = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new QuizResults(
                            rs.getInt("id"),
                            rs.getInt("quiz_id"),
                            userId,
                            rs.getInt("score"),
                            LocalDateTime.parse(rs.getString("taken_at"))
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void deleteQuizResults(QuizResults result) {
        String sql = "DELETE FROM quiz_results WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, result.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
