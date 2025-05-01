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

    public String[] GetOptionTexts() {
        String[] optiontexts = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            optiontexts[i] = options[i].GetOptionText();
        }
        return optiontexts;
    }
}
