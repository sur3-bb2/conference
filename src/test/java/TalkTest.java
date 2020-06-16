import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TalkTest {
    @Test()
    public void shouldReturnInstanceWithProps() {
        String expectedTitle = "Writing Fast Tests Against Enterprise Rails";
        Talk talk = new Talk(expectedTitle, 60, TimeUnit.MINUTES);

        assertTrue(talk.getTitle().equals(expectedTitle));
        assertTrue(talk.getDuration() == 60);
        assertTrue(talk.getDurationUnit().equals(TimeUnit.MINUTES));
        assertTrue(talk.toString().equals("Title : " + expectedTitle + ", Duration : 60 MINUTES"));
    }
}
