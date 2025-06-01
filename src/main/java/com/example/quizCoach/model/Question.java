package com.example.quizCoach.model;

import java.util.Random;

/**
 * Represents a quiz question which can be either multiple-choice or short-answer type.
 */
public class Question {
    private Option selectedOption;
    private int score;
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
    /**
     * Call this after the user picks one of the shuffled options.
     * The passed‐in Option should be the exact object from the current
     * options[] array (e.g. options[i] from GetOptionTexts()).
     * This sets both the selectedOption and computes score = 1 if correct, else 0.
     */
    public void SetSelectedOption(Option choice) {
        this.selectedOption = choice;
        this.score = (choice != null && choice.IsOptionCorrect()) ? 1 : 0;
    }

    /** Returns whichever Option was set via SetSelectedOption(), or null if none. */
    public Option GetSelectedOption() {
        return this.selectedOption;
    }

    /** Returns 1 if the last SetSelectedOption(...) was correct; otherwise 0. */
    public int GetScore() {
        return this.score;
    }
}
