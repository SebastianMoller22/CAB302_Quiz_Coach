package com.example.quizCoach;

import com.example.quizCoach.AI.Get_Sub_topics;
import com.example.quizCoach.AI.Multiple_Choice_Maker;
import com.example.quizCoach.AI.Quiz_Maker;
import com.example.quizCoach.AI.Short_respons_maker;
import com.example.quizCoach.controller.loginController;
import com.example.quizCoach.authentication.AuthenticationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication{



    public static void main(String[] args) throws InterruptedException {


//        Get_Sub_topics test = new Get_Sub_topics("sharks", 5);
//
//        System.out.println(test.getSubtopics().get(0));
//        System.out.println(test.getSubtopics().get(1));
//        System.out.println(test.getSubtopics().get(2));
//        System.out.println(test.getSubtopics().get(3));
//        System.out.println(test.getSubtopics().get(4));


//        Multiple_Choice_Maker test = new Multiple_Choice_Maker(true);
//
//        System.out.println(test.getAnswer());

//        Short_respons_maker test = new Short_respons_maker("Sharks", "evolution", 10);
//        System.out.println(test.getQuestion());

//        Quiz_Maker test = new Quiz_Maker("shark", 10, 1, 1);
//        test.start();
//
//        while (test.isAlive()){
//            System.out.println("waiting");
//            Thread.sleep(500);
//        }

        Quiz_Maker test = new Quiz_Maker(true);

        System.out.println(test.getShortReposnArray().get(0).getQuestion());
        System.out.println(test.getMultipleChoiceArray().get(0).getQuestion());



    }
}
