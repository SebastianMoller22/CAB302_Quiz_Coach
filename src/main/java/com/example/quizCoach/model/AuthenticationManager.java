package com.example.quizCoach.model;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private User activeUser;
    private SqliteUserDAO userDatabase;

    public AuthenticationManager() {
        userDatabase = new SqliteUserDAO();
        activeUser = null;
    }

    public User getActiveUser() {return activeUser;}

    public void setActiveUser(User user) {this.activeUser = user;}

    public Boolean validateString(String the_string, String regex) {
        return the_string.matches(regex);
    }

    public void Signup(String username, String name, String email, String password) throws Exception {
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
        User newuser = new User(username, email, password);
        // Insert into db
        userDatabase.addUser(newuser);
    }

    public Boolean checkifUserExists(String username) {
        List<User> users = userDatabase.getAllUsers();
        List<String> usernames = new ArrayList<>();
        for (User user : users){
            usernames.add(user.getUsername());
        }
        return usernames.contains(username);
    }

    public Boolean matchPasswordandUsername(String username, String password) {
        return false;
    }

    public Boolean LoginAsUser(String username, String password) {
        if (matchPasswordandUsername(username, password)) {
            setActiveUser(getUser(username));
            return true;
        } else {
            return false;
        }
    }

    public void Logout() {
        setActiveUser(null);
    }

    public User getUser(String username) {
        // Return the user with that username
        return new User("Johnny", "aaBB1212@#@#", "hello@example.com");
    }
}
