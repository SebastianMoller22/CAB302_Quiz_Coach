package com.example.quizCoach.model;

/**
 * Constants for AuthenticationManager class
 */
public class AuthenticationConstant {
    /**
     * the regex for checking the validity of usernames
     */
    public static final String usernameRegex = "^[a-zA-Z][a-zA-Z0-9_]{2,19}$";

    /**
     * the regex for checking the validity of email addresses
     */
    public static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$";

    /**
     * the regex for checking the validity of passwords
     */
    public static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$";
}
