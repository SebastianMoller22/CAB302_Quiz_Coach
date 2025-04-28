import com.example.quizCoach.AI.Multiple_Choice_Maker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Multiple_Choice_Maker_Tester {

    private static final String QUESTION = "Which of the following is a primary reason sharks are considered apex predators in most marine ecosystems?";
    private static final String ANSWER = "They have few natural predators themselves.";
    private static final String OPTION1 = "They primarily feed on algae and seaweed.";
    private static final String OPTION2 = "They are typically smaller than most fish species.";
    private static final String OPTION3 = "They rely on symbiotic relationships with other marine animals for survival.";

    private Multiple_Choice_Maker TESTQUESTION;

    @BeforeEach
    public void setUp(){
        TESTQUESTION = new Multiple_Choice_Maker(true);
    }

    @Test
    public void testquestion(){
        assertEquals(QUESTION, TESTQUESTION.getQuestion());
    }

    @Test
    public void testanswer(){
        assertEquals(ANSWER, TESTQUESTION.getAnswer());
    }

    @Test
    public void testoption1(){
        assertEquals(OPTION1, TESTQUESTION.getOptions().get(0));
    }

    @Test
    public void testoption2(){
        assertEquals(OPTION2, TESTQUESTION.getOptions().get(1));
    }

    @Test
    public void testoption3(){
        assertEquals(OPTION3, TESTQUESTION.getOptions().get(2));
    }

}
