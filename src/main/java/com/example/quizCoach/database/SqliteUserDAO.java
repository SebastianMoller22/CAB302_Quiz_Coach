package com.example.quizCoach.database;

import com.example.quizCoach.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * SQLite implementation of IUserDAO. Manages CRUD operations for User objects using an SQLite database.
 */
public class SqliteUserDAO implements IUserDAO {
    private Connection connection;
    /**
     * Constructs a SqliteUserDAO, initializes the database connection, and creates the user table if it does not exist.
     * @throws RuntimeException if a SQLException occurs during initialization.
     */
    public SqliteUserDAO() {
        try {
            connection = SqliteConnection.getInstance();
            createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertSampleData() {
        try {
            // Clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM users";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO user (username, password, email) VALUES "
                    + "('JBells1', 'Password1!','JBells1@example.com'),"
                    + "('JSmith4', 'Password1!','JSmith4@example.com'),"
                    + "('JThom74', 'Password1!','JThom74@example.com')";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates the users table in the SQLite database if it does not already exist.
     * @throws SQLException If a database access error occurs.
     */
    private void createTable() {
        // Create a table if no tables exist
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Adds a new User to the SQLite database. After insertion, the User's ID is set.
     * @param user The User to add.
     */
    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?);");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
            // Set the id of the new user
            ResultSet generaterdKeys = statement.getGeneratedKeys();
            if (generaterdKeys.next()) {
                user.setId(generaterdKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates an existing User in the SQLite database. Finds the User by its ID and updates its fields.
     * @param user The User with updated information.
     */
    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes an existing User from the SQLite database.
     * @param user The User to delete.
     */
    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a User from the SQLite database by its integer ID.
     * @param id The ID of the User to retrieve.
     * @return The User with the given ID, or null if not found.
     */
    @Override
    public User getUser(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(username, password, email);
                user.setId(id);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves a User from the SQLite database by its username.
     * @param username The username of the User to retrieve.
     * @return The User with the given username, or null if not found.
     */
    @Override
    public User getUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(username, password, email);
                user.setId(id);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves all Users from the SQLite database.
     * @return A list of all Users in the database.
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(username, password, email);
                user.setId(id);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
