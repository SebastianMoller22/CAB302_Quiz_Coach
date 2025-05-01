package com.example.quizCoach.model;

import java.util.Random;

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

    private void shuffleQuestion()
    {
        Random rnd = new Random();
        for (int i = options.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Option a = options[index];
            options[index] = options[i];
            options[i] = a;
        }
    }

    public String[] GetOptionTexts() {
        shuffleQuestion();
        String[] optiontexts = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            optiontexts[i] = options[i].GetOptionText();
        }
        return optiontexts;
    }
}
