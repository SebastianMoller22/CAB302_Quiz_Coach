package com.example.quizCoach.model;

public class AuthenticationManager {
    private User activeuser;

    public AuthenticationManager() {
        activeuser = null;
    }

    public User getActiveuser() {return activeuser;}

    public void setActiveuser(User user) {this.activeuser = user;}

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
        if (!validateString(name, AuthenticationConstant.nameRegex)) {
            throw new Exception("invalid Name");
        }
        if (!validateString(email, AuthenticationConstant.emailRegex)) {
            throw new Exception("Invalid Email");
        }
        if (!validateString(password, AuthenticationConstant.passwordRegex)) {
            throw new Exception("Invalid Password");
        }
        User newuser = new User(username, password, email);
        // Insert into db
    }

    public Boolean checkifUserExists(String username) {
        return false;
    }

    public Boolean matchPasswordandUsername(String username, String password) {
        return false;
    }

    public Boolean LoginAsUser(String username, String password) {
        if (matchPasswordandUsername(username, password)) {
            setActiveuser(getUser(username));
            return true;
        } else {
            return false;
        }
    }

    public void Logout() {
        setActiveuser(null);
    }

    public User getUser(String username) {
        // Return the user with that username
        return new User("Johnny", "aaBB1212@#@#", "hello@example.com");
    }
}
