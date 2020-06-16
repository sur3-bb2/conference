import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class SessionTest {
    @Test()
    public void shouldCreateMorningSession() {
        Session componentUnderTest = Session.createMorningSession();

        assertTrue(componentUnderTest.toString().equals("Morning session starts at 9am and ends at 12pm"));
        assertTrue(componentUnderTest.getDuration() == 3 * 60);
    }

    @Test()
    public void shouldCreateEveningSession() {
        Session componentUnderTest = Session.createEveningSession();

        assertTrue(componentUnderTest.toString().equals("Evening session starts at 1pm and ends at 5pm"));
        assertTrue(componentUnderTest.getDuration() == 4 * 60);
    }

    @Test()
    public void shouldAddTalksOrderly() {
        Session componentUnderTest = Session.createEveningSession();
        Talk talk1 = new Talk("title 1", 60, TimeUnit.MINUTES);
        Talk talk2 = new Talk("title 2", 30, TimeUnit.MINUTES);

        componentUnderTest.addTalk(talk1);
        componentUnderTest.addTalk(talk2);


        assertTrue(componentUnderTest.getAllTalks().get(0) == talk1);
        assertTrue(componentUnderTest.getAllTalks().get(1) == talk2);
    }

    @Test()
    public void shouldAddAllTalks() {
        Session componentUnderTest = Session.createEveningSession();
        Talk talk1 = new Talk("title 1", 60, TimeUnit.MINUTES);
        Talk talk2 = new Talk("title 2", 30, TimeUnit.MINUTES);

        componentUnderTest.addTalks(Arrays.asList(new Talk[] { talk1, talk2 }));


        assertTrue(componentUnderTest.getAllTalks().size() == 2);

        assertTrue(componentUnderTest.getAllTalks().get(0) == talk1);
        assertTrue(componentUnderTest.getAllTalks().get(1) == talk2);
    }
}
