package com.example.quizCoach.model;

/**
 * Represents an answer option for a quiz question.
 * Each option contains the option text and whether it is the correct answer.
 */
public class Option {

    /**
     * The textual content of the option.
     */
    private String optionText;

    /**
     * Indicates whether this option is the correct answer.
     */
    private boolean correctOption;

    /**
     * Constructs an Option with the specified text and correctness.
     *
     * @param optionText     the text of the option
     * @param correctOption  true if this option is the correct one, false otherwise
     */
    public Option(String optionText, boolean correctOption) {
        this.optionText = optionText;
        this.correctOption = correctOption;
    }

    /**
     * Returns the text of the option.
     *
     * @return the option text
     */
    public String GetOptionText(){
        return this.optionText;
    }

    /**
     * Returns whether this option is correct.
     *
     * @return true if this is the correct option, false otherwise
     */
    public boolean IsOptionCorrect(){
        return this.correctOption;
    }
}
