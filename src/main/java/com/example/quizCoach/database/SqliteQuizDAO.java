package com.example.quizCoach.database;

import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * SQLite implementation of IQuizDAO. Manages CRUD operations for Quiz objects using an SQLite database.
 */
public class SqliteQuizDAO implements IQuizDAO {
    private Connection connection;
    /**
     * Constructs a SqliteQuizDAO, initializes the database connection, enables foreign keys,
     * and creates the necessary tables if they do not already exist.
     * @throws RuntimeException if a SQLException occurs during initialization.
     */
    public SqliteQuizDAO() {
        try {
            connection = SqliteConnection.getInstance();
            enableForeignKeys();
            createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Enables foreign key enforcement for the SQLite connection.
     * @throws SQLException If a database access error occurs.
     */
    private void enableForeignKeys() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates the necessary database tables (quizzes, questions, options) if they do not exist.
     * @throws SQLException If a database access error occurs.
     */
    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // quizzes table
            stmt.execute("CREATE TABLE IF NOT EXISTS quizzes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "topic VARCHAR NOT NULL,"
                    + "difficulty DOUBLE NOT NULL,"
                    + "user_id INTEGER NOT NULL,"
                    + "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE"
                    + ");");
            // questions table
            stmt.execute("CREATE TABLE IF NOT EXISTS questions ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "quiz_id INTEGER NOT NULL,"
                    + "question_text TEXT NOT NULL,"
                    + "short_response TEXT,"
                    + "selected_option_text TEXT,"
                    + "score INTEGER,"
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
    /**
     * Adds a new Quiz (including its Questions and Options) to the SQLite database.
     * @param quiz The Quiz object to add. After insertion, the Quiz's ID is set.
     */
    @Override
    public void addQuiz(Quiz quiz) {
        String insertQuizSql = "INSERT INTO quizzes (topic, difficulty, user_id) VALUES (?, ?, ?);";
        try (PreparedStatement psQuiz = connection.prepareStatement(insertQuizSql, Statement.RETURN_GENERATED_KEYS)) {
            psQuiz.setString(1, quiz.GetTopic());
            psQuiz.setDouble(2, quiz.GetDifficulty());
            psQuiz.setInt(3, quiz.getCreatedByUserId());
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

    void insertQuestion(int quizId, Question question) throws SQLException {
        String shortAns = question.GetShortResponse();

        if (question.GetOptions() != null && question.GetOptions().length > 0) {
            // Multiple choice
            String insertQuestionSql =
                    "INSERT INTO questions (quiz_id, question_text, selected_option_text, score) VALUES (?, ?, ?, ?);";
            try (PreparedStatement psQ = connection.prepareStatement(insertQuestionSql, Statement.RETURN_GENERATED_KEYS)) {
                psQ.setInt(1, quizId);
                psQ.setString(2, question.GetQuestionText());

                // If no option has been chosen yet, this is null
                if (question.GetSelectedOption() != null) {
                    psQ.setString(3, question.GetSelectedOption().GetOptionText());
                } else {
                    psQ.setNull(3, Types.VARCHAR);
                }
                psQ.setInt(4, question.GetScore());  // 0 or 1 (from Question.GetScore())

                psQ.executeUpdate();
                try (ResultSet rsQ = psQ.getGeneratedKeys()) {
                    if (rsQ.next()) {
                        int questionId = rsQ.getInt(1);
                        // Insert all its Options
                        for (Option opt : question.GetOptions()) {
                            insertOption(questionId, opt);
                        }
                    }
                }
            }
        } else {
            // Short response (no Options)
            String insertQuestionSql =
                    "INSERT INTO questions (quiz_id, question_text, short_response, selected_option_text, score) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement psQ = connection.prepareStatement(insertQuestionSql)) {
                psQ.setInt(1, quizId);
                psQ.setString(2, question.GetQuestionText());
                psQ.setString(3, shortAns);

                // Always null for multiple choice tracking on a short-answer
                psQ.setNull(4, Types.VARCHAR);
                psQ.setInt(5, question.GetScore());  // Typically 0, since no MCQ to score

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
    /**
     * Retrieves a Quiz (and its associated Questions and Options) from the SQLite database by ID.
     * @param id The ID of the Quiz to retrieve.
     * @return The Quiz with the given ID, or null if not found.
     */
    @Override
    public Quiz getQuiz(int id) {
        Quiz quiz = null;
        String selectQuizSql = "SELECT topic, difficulty, user_id FROM quizzes WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(selectQuizSql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String topic = rs.getString("topic");
                    double difficulty = rs.getFloat("difficulty");
                    int userId = rs.getInt("user_id");
                    Question[] questions = loadQuestions(id);
                    quiz = new Quiz(topic, difficulty, questions, userId);
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
                "SELECT id, question_text, short_response, selected_option_text, score "
                        + "FROM questions WHERE quiz_id = ?;";
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement psQ = connection.prepareStatement(selectQuestionsSql)) {
            psQ.setInt(1, quizId);
            try (ResultSet rsQ = psQ.executeQuery()) {
                while (rsQ.next()) {
                    int questionId        = rsQ.getInt("id");
                    String text           = rsQ.getString("question_text");
                    String shortAns       = rsQ.getString("short_response");
                    String selectedText   = rsQ.getString("selected_option_text");
                    int storedScore     = rsQ.getInt("score");

                    if (shortAns != null && !shortAns.isEmpty()) {
                        // Short response
                        Question q = new Question(text, shortAns);
                        questions.add(q);
                    } else {
                        // Multiple Choice
                        Option[] options = loadOptions(questionId);
                        Question q = new Question(text, options);

                        if (selectedText != null && !selectedText.isEmpty()) {
                            // Find the matching Option instance by text
                            for (Option opt : options) {
                                if (opt.GetOptionText().equals(selectedText)) {
                                    q.SetSelectedOption(opt);
                                    break;
                                }
                            }
                        }
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
    /**
     * Retrieves all quizzes (including their Questions and Options) from the SQLite database.
     * @return A list of all quizzes in the database.
     */
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
    /**
     * Updates an existing Quiz (including its Questions and Options) in the SQLite database.
     * All previous Questions and Options for that Quiz are deleted and replaced.
     * @param quiz The Quiz object with updated information.
     */
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
    /**
     * Deletes an existing Quiz (and its associated Questions and Options) from the SQLite database.
     * @param quiz The Quiz to delete.
     */
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