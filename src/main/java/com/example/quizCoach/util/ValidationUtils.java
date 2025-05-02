package com.example.quizCoach.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating email addresses and password strength.
 */
public class ValidationUtils {
    // Basic email pattern: letters, digits, plus, underscore, dot, hyphen before '@';
    // domain part allows letters, digits, hyphens, and dots.
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Password must be at least 8 characters,
    // and include at least one digit, one lowercase, one uppercase, and one special character.
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * Validates whether the given email string matches a basic email pattern.
     *
     * @param email the email address to validate
     * @return true if email is non-null and matches pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates whether the given password string is strong enough.
     *
     * @param password the password to validate
     * @return true if password is non-null and meets complexity requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}