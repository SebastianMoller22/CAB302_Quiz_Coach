package com.example.quizCoach.model;

public class Option {
    private String optionText;
    private boolean correctOption;

    public Option(String optionText, boolean correctOption) {
        this.optionText = optionText;
        this.correctOption = correctOption;
    }

    public String GetOptionText(){
        return this.optionText;
    }

    public boolean IsOptionCorrect(){
        return this.correctOption;
    }
}
