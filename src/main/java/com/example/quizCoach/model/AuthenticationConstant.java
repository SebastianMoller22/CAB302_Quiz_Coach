package com.example.quizCoach.model;

public class AuthenticationConstant {
    public static final String usernameRegex = "^[a-zA-Z][a-zA-Z0-9_]{2,19}$";
    public static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$";
    public static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$";
}
