package com.example.quizCoach.Session;

import com.example.quizCoach.authentication.AuthenticationManager;
import com.example.quizCoach.model.QuizManager;

public class SessionManager {
    private QuizManager quizManager;

    private AuthenticationManager authenticationManager;

    public  SessionManager() {
        quizManager = new QuizManager();
        authenticationManager = new AuthenticationManager();
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }

    public  AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setQuizManager(QuizManager quizManager) { this.quizManager = quizManager; }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) { this.authenticationManager = authenticationManager; }
}
