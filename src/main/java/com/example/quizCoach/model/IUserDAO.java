package com.example.quizCoach.model;

import java.util.List;

/**
 * Interface for the User Data Access Object that handles
 * the CRUD operations for the User class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new User to the database.
     * @param user The User to add.
     */
    public void addUser(User user);
    /**
     * Updates an existing User in the database.
     * @param user The User to update.
     */
    public void updateUser(User user);
    /**
     * Deletes a User from the database.
     * @param user The User to delete.
     */
    public void deleteUser(User user);
    /**
<<<<<<< HEAD
     * Retrieves a User from the database using an id.
=======
     * Retrieves a User from the database.
>>>>>>> gui
     * @param id The id of the User to retrieve.
     * @return The User with the given id, or null if not found.
     */
    public User getUser(int id);
    /**
<<<<<<< HEAD
     * Retrieves a User from the database using a username.
     * @param username The username of the User to retrieve.
     * @return The User with the given username, or null if not found.
     */
    public User getUser(String username);
    /**
=======
>>>>>>> gui
     * Retrieves all Users from the database.
     * @return A list of all Users in the database.
     */
    public List<User> getAllUsers();
}

