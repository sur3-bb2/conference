import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class ConferenceManagerTest {
    List<Talk> talks;
    Conference conference;
    ConferenceManager componentUnderTest;
    TrackInput trackInput;

    @Before
    public void setup() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("input2.txt");
        URL trackInputUrl = this.getClass().getClassLoader().getResource("TrackInput.json");

        String test = new String(Files.readAllBytes(Paths.get(url.getFile())));
        String trackInputJson = new String(Files.readAllBytes(Paths.get(trackInputUrl.getFile())));

        List<Talk> talks = TalkBuilder.builder()
                .from(test)
                .using(new RightToLeftParser())
                .sort(Comparator.comparing(Talk::getDuration), true)
                .build();
        this.trackInput = new TrackInput();
        Session[] sessionsInput = new ObjectMapper().readValue(trackInputJson, Session[].class);

        trackInput.setSessions(Arrays.asList(sessionsInput));

        this.conference = new Conference(talks);
        this.componentUnderTest = new ConferenceManager(conference);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionForNullInput() {
        new ConferenceManager(null);
    }

    @Test
    public void shouldPrepareAllTalkSchedules() throws IOException {
        List<Track> actual = this.componentUnderTest.getTracks();

        assertTrue(actual.size() > 0);

        long morningSessions = actual.stream().flatMap(t -> t.getMorningSession().getAllTalks().stream()).count();
        long eveningSessions = actual.stream().flatMap(t -> t.getEveningSession().getAllTalks().stream()).count();

        assertTrue(morningSessions + eveningSessions == talks.size());

        printResults(actual);
    }

    @Test
    public void shouldPrepareAllTalk() throws ParseException {
        List<Track> actual = this.componentUnderTest.getTracks(trackInput);

        assertTrue(actual.size() > 0);

        printNewResults(actual);
    }

    private void printResults(List<Track> actual) {
        System.out.println("---------output---------------");
        actual.forEach(t -> {
            System.out.println("---------Track start---------------");

            System.out.println(t.getMorningSession().toString());
            t.getMorningSession().getAllTalks().forEach(talk -> {
                System.out.println(talk.toString());
            });

            System.out.println(t.getEveningSession().toString());
            t.getEveningSession().getAllTalks().forEach(talk -> {
                System.out.println(talk.toString());
            });

            System.out.println("---------Track end---------------");
        });
        System.out.println("---------------------------");
    }

    private void printNewResults(List<Track> actual) {
        System.out.println("---------output---------------");
        actual.forEach(t -> {
            System.out.println("---------Track start---------------");

            t.getSession().forEach(s-> {
                System.out.println(s.toString());

                s.getAllTalks().forEach(talk -> {
                    System.out.println(talk.toString());
                });
            });

            System.out.println("---------Track end---------------");
        });
        System.out.println("---------------------------");
    }
}
