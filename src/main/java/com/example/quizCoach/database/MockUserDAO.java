package com.example.quizCoach.database;

import com.example.quizCoach.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of IUserDAO that stores users in memory for testing purposes.
 * This class maintains an in-memory list of User objects and simulates auto-incremented IDs.
 */
public class MockUserDAO implements IUserDAO {
    /**
     * A static list of users to be used as a mock database.
     */
    public static final ArrayList<User> USERS = new ArrayList<>();
    private static int autoIncrementedId = 0;

    /**
     * Constructs a MockUserDAO and seeds the in-memory list with initial users.
     */
    public MockUserDAO() {
        // Add some initial users to the mock database
        User alice = new User("alice", "password123", "alice@example.com");
        alice.setId(++autoIncrementedId);
        USERS.add(alice);

        User bob = new User("bob", "password456", "bob@example.com");
        bob.setId(++autoIncrementedId);
        USERS.add(bob);
    }

    /**
     * Adds a new User to the in-memory list. Assigns an auto-incremented ID to the User.
     * @param user The User to add.
     */
    @Override
    public void addUser(User user) {
        user.setId(++autoIncrementedId);
        USERS.add(user);
    }

    /**
     * Updates an existing User in the in-memory list. Finds the User by its ID and replaces its data.
     * @param user The User with updated information.
     */
    @Override
    public void updateUser(User user) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).getId() == user.getId()) {
                USERS.set(i, user);
                return;
            }
        }
    }

    /**
     * Deletes an existing User from the in-memory list.
     * @param user The User to delete.
     */
    @Override
    public void deleteUser(User user) {
        USERS.removeIf(u -> u.getId() == user.getId());
    }

    /**
     * Retrieves a User from the database using an id.
     *
     * @param id The id of the User to retrieve.
     * @return The User with the given id, or null if not found.
     */
    @Override
    public User getUser(int id) {
        return null;
    }

    /**
     * Retrieves a User from the in-memory list by username.
     * @param username The username of the User to retrieve.
     * @return The User with the given username, or null if not found.
     */
    @Override
    public User getUser(String username) {
        for (User user : USERS) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves all Users from the in-memory list.
     * @return A list of all Users.
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(USERS);
    }
}
