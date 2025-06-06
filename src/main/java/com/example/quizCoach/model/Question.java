package com.example.quizCoach.model;

import java.util.Random;

/**
 * Represents a quiz question which can be either multiple-choice or short-answer type.
 */
public class Question {
    /**
     * The option that the user select.
     */
    private Option selectedOption;

    /**
     * The score that the user get for the question.
     */
    private int score = 0;

    /**
     * The text of the question.
     */
    private String questionText;

    /**
     * An array of answer options for multiple-choice questions.
     */
    private Option[] options;

    /**
     * The expected short response for short-answer questions.
     */
    private String shortResponse;

    /**
     * Constructs a multiple-choice question with specified text and options.
     *
     * @param questionText the text of the question
     * @param options      the array of answer options
     */
    public Question(String questionText, Option[] options) {
        this.questionText = questionText;
        this.options = options;
    }

    /**
     * Constructs a short-answer question with specified text and expected response.
     *
     * @param questionText the text of the question
     * @param shortResponse the expected short answer
     */
    public Question(String questionText, String shortResponse) {
        this.questionText = questionText;
        this.shortResponse = shortResponse;
    }

    /**
     * Returns the text of the correct option, if available.
     *
     * @return the correct option's text, or an empty string if none is found
     */
    public  String GetCorrectOptionText() {
        for (Option option : options) {
            if (option.IsOptionCorrect()) {
                return option.GetOptionText();
            }
        }
        return "";
    }

    /**
     * Returns the text of the question.
     *
     * @return the question text
     */
    public String GetQuestionText() {
        return this.questionText;
    }

    /**
     * Returns the array of answer options.
     *
     * @return an array of {@link Option}
     */
    public Option[] GetOptions() {
        return this.options;
    }

    /**
     * Returns the expected short response for the question.
     *
     * @return the short response
     */
    public String GetShortResponse() {
        return this.shortResponse;
    }

    /**
     * Randomly shuffles the order of the options using Fisher-Yates algorithm.
     * Used internally to prevent answer pattern memorization.
     */
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

    /**
     * Returns the text of all options after shuffling their order.
     *
     * @return an array of option texts
     */
    public String[] GetOptionTexts() {
        shuffleQuestion();
        String[] optiontexts = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            optiontexts[i] = options[i].GetOptionText();
        }
        return optiontexts;
    }

    /***
     * Set the selected choice to the choice that the user chose
     *
     * @param choice the choice that the user chose
     */
    public void SetSelectedOption(String choice) {
        for (Option option: options) {
            if (option.GetOptionText().equals(choice)) {
                this.selectedOption = option;
                this.score = (option != null && option.IsOptionCorrect()) ? 1 : 0;
            }
        }

    }

    /***
     * @return the selected option
     */
    public Option GetSelectedOption() {
        return this.selectedOption;
    }

    /***
     * @return the score that the user get for the question
     */
    public int GetScore() {
        return this.score;
    }
}
