package databaseTests;

import static org.junit.jupiter.api.Assertions.*;

import com.example.quizCoach.model.User;
import com.example.quizCoach.database.SqliteConnection;
import com.example.quizCoach.database.SqliteUserDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

class SqliteUserDAOTest {

    private SqliteUserDAO sqliteUserDAO;

    @BeforeEach
    void setUp() throws Exception {
        // change the connection to run in memory
        SqliteConnection.init("jdbc:sqlite::memory:");
        Connection conn = SqliteConnection.getInstance();
        sqliteUserDAO = new SqliteUserDAO();
    }

    @Test
    void addAndGetUser() throws Exception {
        User u = new User("alice","pw123","alice@example.com");
        sqliteUserDAO.addUser(u);

        assertTrue(u.getId() > 0, "should assign an auto-incremented ID");
        User fetched = sqliteUserDAO.getUser("alice");
        assertNotNull(fetched);
        assertEquals("alice",  fetched.getUsername());
        assertEquals("alice@example.com", fetched.getEmail());
    }

    @Test
    void getAllUsers_empty_thenFilled() throws Exception {
        assertTrue(sqliteUserDAO.getAllUsers().isEmpty());

        sqliteUserDAO.addUser(new User("bob","pw","bob@x"));
        List<User> all = sqliteUserDAO.getAllUsers();
        assertEquals(1, all.size());
        assertEquals("bob", all.get(0).getUsername());
    }

    @Test
    void updateUser_changesEmail() throws Exception {
        User u = new User("carol","pw","c@x");
        sqliteUserDAO.addUser(u);

        u.setEmail("carol@new");
        sqliteUserDAO.updateUser(u);

        User refreshed = sqliteUserDAO.getUser("carol");
        assertEquals("carol@new", refreshed.getEmail());
    }

    @Test
    void deleteUser_removesRecord() throws Exception {
        User u = new User("dan","pw","d@x");
        sqliteUserDAO.addUser(u);
        assertNotNull(sqliteUserDAO.getUser("dan"));

        sqliteUserDAO.deleteUser(u);
        assertNull(sqliteUserDAO.getUser("dan"));
        assertTrue(sqliteUserDAO.getAllUsers().isEmpty());
    }
}
