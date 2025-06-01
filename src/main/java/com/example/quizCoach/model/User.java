package com.example.quizCoach.model;


/**
 * Represents a user of the system, containing login credentials and contact information.
 */
public class User {


    /**
     * Unique identifier for the user.
     */
    private int id;

    /**
     * Username selected by the user.
     */
    private String username;

    /**
     * Hashed password of the user.
     */
    private String password;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Constructs a new User with the given username, password, and email.
     *
     * @param username the username of the user
     * @param password the password (hashed) of the user
     * @param email    the email address of the user
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the user's unique ID.
     *
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's unique ID.
     *
     * @param id the user ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's password (should be hashed).
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password (should be hashed before setting).
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
