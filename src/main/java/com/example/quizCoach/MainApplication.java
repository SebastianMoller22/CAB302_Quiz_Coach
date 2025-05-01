package com.example.quizCoach;

import com.example.quizCoach.AI.Multiple_Choice_Maker;
import com.example.quizCoach.AI.Quiz_Maker;


public class MainApplication {


    public static void main(String[] args) {

        Quiz_Maker Test = new Quiz_Maker("sharks", 100, 2, 0);
        System.out.println(Test.getMultipleChoiceArray().get(0).getQuestion());
        System.out.println(Test.getMultipleChoiceArray().get(0).getAnswer());
        System.out.println(Test.getMultipleChoiceArray().get(0).getOptions().get(0));
        Test.UploadJSON();
    }


}