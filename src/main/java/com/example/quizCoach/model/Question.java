package com.example.quizCoach.model;

public class Question {
    private String questionText;
    private Option[] options;

    public Question(String questionText, Option[] options) {
        this.questionText = questionText;
        this.options = options;
    }

    public String GetQuestionText() {
        return this.questionText;
    }

    public Option[] GetOptions() {
        return this.options;
    }
}
