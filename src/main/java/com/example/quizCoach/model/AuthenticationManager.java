package com.example.quizCoach.model;

public class AuthenticationManager {
    private User activeuser;

    public User getActiveuser() {return activeuser;}

    public void setActiveuser(User user) {this.activeuser = user;}

    public Boolean validateString(String the_string, String regex) {
        return the_string.matches(regex);
    }

    public Boolean checkifUserExists(String username) {
        return false;
    }

    public Boolean matchPasswordandUsername(String username, String password) {
        return false;
    }

    public Boolean LoginAsUser(String username, String password) {
        if (matchPasswordandUsername(username, password)) {
            activeuser = getUser(username);
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username) {
        // Return the user with that username
        return new User("Johnny", "name", "hello@example.com", "aaBB1212@#@#");
    }
}
