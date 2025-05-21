package databaseTests;

import static org.junit.jupiter.api.Assertions.*;

import com.example.quizCoach.model.Option;
import com.example.quizCoach.model.Question;
import com.example.quizCoach.model.Quiz;
import com.example.quizCoach.database.SqliteQuizDAO;
import com.example.quizCoach.database.SqliteConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

class SqliteQuizDAOTest {

    private SqliteQuizDAO quizDao;

    @BeforeEach
    void setUp() throws Exception {
        SqliteConnection.init("jdbc:sqlite::memory:");
        Connection conn = SqliteConnection.getInstance();
        quizDao = new SqliteQuizDAO();
    }

    @Test
    void addAndGetQuiz_withOneQuestionOneOption() {
        // build a quiz with one question and one option
        Option opt = new Option("Answer A", true);
        Question q = new Question("What is A?", new Option[]{ opt });
        Quiz quiz = new Quiz("Sample Topic", 2.5, new Question[]{ q });

        quizDao.addQuiz(quiz);
        assertTrue(quiz.GetQuizID() > 0, "should have assigned an ID");

        Quiz loaded = quizDao.getQuiz(quiz.GetQuizID());
        assertNotNull(loaded, "retrieved quiz must not be null");
        assertEquals("Sample Topic", loaded.GetTopic());
        assertEquals(2.5, loaded.GetDifficulty(), 1e-6);

        // verify questions & options round-trip
        Question[] loadedQs = loaded.GetQuestions();
        assertEquals(1, loadedQs.length, "should have one question");
        assertEquals("What is A?", loadedQs[0].GetQuestionText());

        String[] opts = loadedQs[0].GetOptionTexts();
        assertEquals(1, opts.length, "should have one option text");
        assertEquals("Answer A", opts[0]);
    }

    @Test
    void getAllQuizzes_returnsInsertedQuizzes() {
        Quiz q1 = new Quiz("T1", 1.0, new Question[]{});
        Quiz q2 = new Quiz("T2", 3.0, new Question[]{});
        quizDao.addQuiz(q1);
        quizDao.addQuiz(q2);

        List<Quiz> all = quizDao.getAllQuizzes();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(q->"T1".equals(q.GetTopic())));
        assertTrue(all.stream().anyMatch(q->"T2".equals(q.GetTopic())));
    }

    @Test
    void deleteQuiz_removesItAndCascadeDeletes() {
        // create and verify presence
        Quiz q = new Quiz("ToDelete", 0.5, new Question[]{});
        quizDao.addQuiz(q);
        assertNotNull(quizDao.getQuiz(q.GetQuizID()));

        // delete and verify gone
        quizDao.deleteQuiz(q);
        assertNull(quizDao.getQuiz(q.GetQuizID()));
        assertTrue(quizDao.getAllQuizzes().isEmpty());
    }
}
