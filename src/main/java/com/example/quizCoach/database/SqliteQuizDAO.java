package com.example.quizCoach.database;

import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteQuizDAO implements IQuizDAO {
    private Connection connection;

    public SqliteQuizDAO() {
        connection = SqliteConnection.getInstance();
        enableForeignKeys();
        createTables();
    }

    private void enableForeignKeys() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // quizzes table
            stmt.execute("CREATE TABLE IF NOT EXISTS quizzes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "topic VARCHAR NOT NULL,"
                    + "difficulty DOUBLE NOT NULL"
                    + ");");
            // questions table
            stmt.execute("CREATE TABLE IF NOT EXISTS questions ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "quiz_id INTEGER NOT NULL,"
                    + "question_text TEXT NOT NULL,"
                    + "short_resopnse TEXT"
                    + "FOREIGN KEY(quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE"
                    + ");");
            // options table
            stmt.execute("CREATE TABLE IF NOT EXISTS options ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "question_id INTEGER NOT NULL,"
                    + "option_text TEXT NOT NULL,"
                    + "is_correct INTEGER NOT NULL,"
                    + "FOREIGN KEY(question_id) REFERENCES questions(id) ON DELETE CASCADE"
                    + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addQuiz(Quiz quiz) {
        String insertQuizSql = "INSERT INTO quizzes (topic, difficulty) VALUES (?, ?);";
        try (PreparedStatement psQuiz = connection.prepareStatement(insertQuizSql, Statement.RETURN_GENERATED_KEYS)) {
            psQuiz.setString(1, quiz.GetTopic());
            psQuiz.setDouble(2, quiz.GetDifficulty());
            psQuiz.executeUpdate();
            try (ResultSet rs = psQuiz.getGeneratedKeys()) {
                if (rs.next()) {
                    quiz.SetQuizID(rs.getInt(1));
                }
            }
            // insert questions and options
            for (Question q : quiz.GetQuestions()) {
                insertQuestion(quiz.GetQuizID(), q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertQuestion(int quizId, Question question) throws SQLException {
        String[] mcOptions = null;
        String shortAns = question.GetShortResponse();

        if (question.GetOptions() != null && question.GetOptions().length > 0) {
            // Multiple choice
            String insertQuestionSql =
                    "INSERT INTO questions (quiz_id, question_text) VALUES (?, ?);";
            try (PreparedStatement psQ = connection.prepareStatement(insertQuestionSql, Statement.RETURN_GENERATED_KEYS)) {
                psQ.setInt(1, quizId);
                psQ.setString(2, question.GetQuestionText());
                psQ.executeUpdate();
                try (ResultSet rsQ = psQ.getGeneratedKeys()) {
                    if (rsQ.next()) {
                        int questionId = rsQ.getInt(1);
                        for (Option opt : question.GetOptions()) {
                            insertOption(questionId, opt);
                        }
                    }
                }
            }
        } else {
            // Short response
            String insertQuestionSql =
                    "INSERT INTO questions (quiz_id, question_text, short_response) VALUES (?, ?, ?);";
            try (PreparedStatement psQ = connection.prepareStatement(insertQuestionSql, Statement.RETURN_GENERATED_KEYS)) {
                psQ.setInt(1, quizId);
                psQ.setString(2, question.GetQuestionText());
                psQ.setString(3, shortAns);
                psQ.executeUpdate();
            }
        }
    }

    private void insertOption(int questionId, Option option) throws SQLException {
        String insertOptionSql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?);";
        try (PreparedStatement psO = connection.prepareStatement(insertOptionSql)) {
            psO.setInt(1, questionId);
            psO.setString(2, option.GetOptionText());
            psO.setInt(3, option.IsOptionCorrect() ? 1 : 0);
            psO.executeUpdate();
        }
    }

    @Override
    public Quiz getQuiz(int id) {
        Quiz quiz = null;
        String selectQuizSql = "SELECT topic, difficulty FROM quizzes WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(selectQuizSql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String topic = rs.getString("topic");
                    double difficulty = rs.getFloat("difficulty");
                    Question[] questions = loadQuestions(id);
                    quiz = new Quiz(topic, difficulty, questions);
                    quiz.SetQuizID(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    private Question[] loadQuestions(int quizId) throws SQLException {
        String selectQuestionsSql =
                "SELECT id, question_text, short_response FROM questions WHERE quiz_id = ?;";
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement psQ = connection.prepareStatement(selectQuestionsSql)) {
            psQ.setInt(1, quizId);
            try (ResultSet rsQ = psQ.executeQuery()) {
                while (rsQ.next()) {
                    int questionId    = rsQ.getInt("id");
                    String text       = rsQ.getString("question_text");
                    String shortAns   = rsQ.getString("short_response");

                    if (shortAns != null && !shortAns.isEmpty()) {
                        // Short response
                        Question q = new Question(text, shortAns);
                        questions.add(q);
                    } else {
                        // Multiple Choice
                        Option[] options = loadOptions(questionId);
                        Question q = new Question(text, options);
                        questions.add(q);
                    }
                }
            }
        }
        return questions.toArray(new Question[0]);
    }


    private Option[] loadOptions(int questionId) throws SQLException {
        String selectOptionsSql = "SELECT option_text, is_correct FROM options WHERE question_id = ?;";
        List<Option> options = new ArrayList<>();
        try (PreparedStatement psO = connection.prepareStatement(selectOptionsSql)) {
            psO.setInt(1, questionId);
            try (ResultSet rsO = psO.executeQuery()) {
                while (rsO.next()) {
                    String text = rsO.getString("option_text");
                    boolean isCorrect = rsO.getInt("is_correct") == 1;
                    options.add(new Option(text, isCorrect));
                }
            }
        }
        return options.toArray(new Option[0]);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String selectAllSql = "SELECT id FROM quizzes;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllSql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Quiz quiz = getQuiz(id);
                if (quiz != null) quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        String updateQuizSql = "UPDATE quizzes SET topic = ?, difficulty = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(updateQuizSql)) {
            ps.setString(1, quiz.GetTopic());
            ps.setDouble(2, quiz.GetDifficulty());
            ps.setInt(3, quiz.GetQuizID());
            ps.executeUpdate();
            deleteQuestionsByQuizId(quiz.GetQuizID());
            for (Question q : quiz.GetQuestions()) {
                insertQuestion(quiz.GetQuizID(), q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteQuestionsByQuizId(int quizId) throws SQLException {
        String deleteOptionsSql = "DELETE FROM options WHERE question_id IN (SELECT id FROM questions WHERE quiz_id = ?);";
        try (PreparedStatement psO = connection.prepareStatement(deleteOptionsSql)) {
            psO.setInt(1, quizId);
            psO.executeUpdate();
        }
        String deleteQuestionsSql = "DELETE FROM questions WHERE quiz_id = ?;";
        try (PreparedStatement psQ = connection.prepareStatement(deleteQuestionsSql)) {
            psQ.setInt(1, quizId);
            psQ.executeUpdate();
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        String deleteQuizSql = "DELETE FROM quizzes WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(deleteQuizSql)) {
            ps.setInt(1, quiz.GetQuizID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}