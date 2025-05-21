package com.example.quizCoach.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static String url = "jdbc:sqlite:quizCoach.db";
    private static Connection conn;

    /**
     * Used for overriding the Database url. Must be called before getInstance().
     * @param jdbcUrl the url you want to override with
     * */
    public static synchronized void init(String jdbcUrl) {
        url = jdbcUrl;
        // if there is already connection, close it so it can be re-opened with the new URL
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
            conn = null;
        }
    }

    /**
     * Returns a Connection.  If no connection exists, or it's closed, opens a new one.
     * @return Connection New connection to be return
     */
    public static synchronized Connection getInstance() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url);
            try {
                conn.createStatement().execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                // ignore if not supported
            }
        }
        return conn;
    }
    // private constructor so instances of it can't be created because all its methods and state are static
    private SqliteConnection() {}
}
