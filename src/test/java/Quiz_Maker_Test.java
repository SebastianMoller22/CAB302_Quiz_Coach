import com.example.quizCoach.AI.Quiz_Maker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Quiz_Maker_Test {

    private static final String TOPIC = "Sharks";
    private static final int SKILL_LEVEL = 1;
    private static final int NUM_MC = 1;
    private static final int NUM_SR = 0;



    private static final String QUESTION = "Which of the following is a primary reason sharks are considered apex predators in most marine ecosystems?";
    private static final String ANSWER = "They have few natural predators themselves.";
    private static final String OPTION1 = "They primarily feed on algae and seaweed.";
    private static final String OPTION2 = "They are typically smaller than most fish species.";
    private static final String OPTION3 = "They rely on symbiotic relationships with other marine animals for survival.";

    private Quiz_Maker TESTQUIZ;

    @BeforeEach
    public void setUp(){
        TESTQUIZ = new Quiz_Maker(true);
    }

    @Test
    public void testtopic(){
        assertEquals(TOPIC, TESTQUIZ.getTopic());
    }

    @Test
    public void testskillleve(){
        assertEquals(SKILL_LEVEL, TESTQUIZ.getSkill_level());
    }

    @Test
    public void testnumMC(){
        assertEquals(NUM_MC, TESTQUIZ.getNumMultipleChoice());
    }

    @Test
    public void testnumSR(){
        assertEquals(NUM_SR, TESTQUIZ.getNumShortResponse());
    }

    @Test
    public void testquestion(){
        assertEquals(QUESTION, TESTQUIZ.getMultipleChoiceArray().get(0).getQuestion());
    }

    @Test
    public void testanswer(){
        assertEquals(ANSWER, TESTQUIZ.getMultipleChoiceArray().get(0).getAnswer());
    }

    @Test
    public void testoption1(){
        assertEquals(OPTION1, TESTQUIZ.getMultipleChoiceArray().get(0).getOptions().get(0));
    }

    @Test
    public void testoption2(){
        assertEquals(OPTION2, TESTQUIZ.getMultipleChoiceArray().get(0).getOptions().get(1));
    }

    @Test
    public void testoption3(){
        assertEquals(OPTION3, TESTQUIZ.getMultipleChoiceArray().get(0).getOptions().get(2));
    }

    @Test
    public void testIndividualscore1(){
        assertEquals(0, TESTQUIZ.getMultipleChoiceArray().get(0).getScore());
    }

    @Test
    public void testIndividualscore2(){
        TESTQUIZ.getMultipleChoiceArray().get(0).setScore(1);
        assertEquals(1, TESTQUIZ.getMultipleChoiceArray().get(0).getScore());
    }

    @Test
    public void testtotalscore1(){
        assertEquals(0, TESTQUIZ.getTotalScore());
    }

    @Test
    public void testtotalscore2(){
        TESTQUIZ.setTotalScore(1);
        assertEquals(1, TESTQUIZ.getTotalScore());
    }




}