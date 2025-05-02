package com.example.quizCoach.authentication;

import com.example.quizCoach.database.SqliteUserDAO;
import com.example.quizCoach.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * For Signup/Login/Logout and add/modify users' database
 */
public class AuthenticationManager {
    /**
     * the user using the session
     */
    private User activeUser;

    /**
     * the users' database
     */
    private SqliteUserDAO userDatabase;

    /**
     * the default constructor of the class
     */
    public AuthenticationManager() {
        userDatabase = new SqliteUserDAO();
        activeUser = null;
    }

    /**
     * the method for getting the active user
     * @return the current user of the session
     */
    public User getActiveUser() {return activeUser;}

    /**
     * set the active user to an user
     * @param user the user you want to set
     */
    public void setActiveUser(User user) {this.activeUser = user;}

    /**
     * for validating users' inputs
     * @param the_string the string you want to validate
     * @param regex the regex you want to compare with
     * @return whether the string is valid or not
     */
    public Boolean validateString(String the_string, String regex) {
        return the_string.matches(regex);
    }

    /**
     * for signing up as an user
     * @param username a distinct username
     * @param email the user's email
     * @param password the user's password
     * @throws Exception if any of the string is invalid
     */
    public void Signup(String username, String email, String password) throws Exception {
        if (!validateString(username, AuthenticationConstant.usernameRegex)){
            throw new Exception("Invalid Username");
        }
        if (checkifUserExists(username)) {
            throw new Exception("This Username already existed");
        }
        if (!validateString(email, AuthenticationConstant.emailRegex)) {
            throw new Exception("Invalid Email");
        }
        if (!validateString(password, AuthenticationConstant.passwordRegex)) {
            throw new Exception("Invalid Password");
        }
        User newuser = new User(username, password, email);
        // Insert into db
        userDatabase.addUser(newuser);
    }

    /**
     * Confirm whether the username has been used or not
     * @param username the username you want to check
     * @return whether the username has been used
     */
    public Boolean checkifUserExists(String username) {
        List<User> users = userDatabase.getAllUsers();
        List<String> usernames = new ArrayList<>();
        for (User user : users){
            usernames.add(user.getUsername());
        }
        return usernames.contains(username);
    }

    /**
     * to confirm whether the username and password match
     * @param username the user's username
     * @param password the user's password
     * @return if the username and password match
     * @throws Exception if they do not match
     */
    public Boolean matchPasswordandUsername(String username, String password) throws Exception {
        User user = userDatabase.getUser(username);
        if (!checkifUserExists(username)) {
            throw new Exception("No User with this username");
        }
        return user.getPassword().equals(password);
    }

    /**
     * Used by the users to login to their accounts
     * @param username the user's username
     * @param password the user's password
     * @return if the login complete
     * @throws Exception if username and password do not match
     */
    public Boolean LoginAsUser(String username, String password) throws Exception {
        if (matchPasswordandUsername(username, password)) {
            setActiveUser(getUser(username));
            return true;
        }
        return false;
    }

    /**
     * Log out of the account
     */
    public void Logout() {
        setActiveUser(null);
    }

    /**
     * to get the user from the username
     * @param username the username
     * @return the user with the matching username
     */
    public User getUser(String username) {
        // Return the user with that username
        return new User("Johnny", "aaBB1212@#@#", "hello@example.com");
    }
}
