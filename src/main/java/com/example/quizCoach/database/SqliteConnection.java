package com.example.quizCoach.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages a singleton connection to an SQLite database.
 * Provides methods to initialize the database URL and to retrieve the Connection instance.
 */
public class SqliteConnection {
    private static String url = "jdbc:sqlite:quizCoach.db";
    private static Connection conn;

    /**
     * Used for overriding the database URL. Must be called before getInstance().
     * @param jdbcUrl the JDBC URL to override with.
     */
    public static synchronized void init(String jdbcUrl) {
        url = jdbcUrl;
        // If there is already a connection, close it so it can be re-opened with the new URL
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a singleton Connection to the SQLite database. If not already connected (or if closed),
     * a new connection is created. Foreign key support is enabled if possible.
     * @return The Connection instance.
     * @throws SQLException If a database access error occurs.
     */
    public static synchronized Connection getInstance() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url);
            try {
                conn.createStatement().execute("PRAGMA foreign_keys = ON;");
            } catch (SQLException e) {
                // Ignore if not supported
            }
        }
        return conn;
    }

    /**
     * Private constructor to prevent instantiation. All methods and state are static.
     */
    private SqliteConnection() {}
}
