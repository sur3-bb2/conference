import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class ConferenceTest {
    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionForNullInput() {
        Conference componentUnderTest = new Conference(null);
    }

    @Test()
    public void shouldGetCreatedSuccessfully() {
        List<Talk> talks = new ArrayList<Talk>();

        Conference componentUnderTest = new Conference(talks);

        assertNotNull(componentUnderTest);
    }

    @Test
    public void shouldPrepareTalkSchedules() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("input.txt");
        String test = new String(Files.readAllBytes(Paths.get(url.getFile())));
        List<Talk> talks = TalkBuilder.builder()
                .from(test)
                .using(new RightToLeftParser())
                .sort(Comparator.comparing(Talk::getDuration), true)
                .build();

        ArrayList<String> actualFileContents = new ArrayList<>();
        Conference componentUnderTest = new Conference(talks);

        while (true) {
            List<Talk> actualTalksFor5hrs = componentUnderTest.getTalksByTime(5 * 60);

            if(actualTalksFor5hrs == null || actualTalksFor5hrs.isEmpty()) {
                break;
            }

            actualFileContents.addAll(actualTalksFor5hrs
                    .stream()
                    .map(a -> a.toString())
                    .collect(Collectors.toList()));
        }

        assertTrue(actualFileContents.size() == talks.size());

        talks.forEach(talk -> {
            actualFileContents.contains(talk.toString());
        });
    }

    @Test
    public void shouldSchedulesAllTalks() throws IOException {
        List<Talk> talks = new ArrayList<>();

        talks.add(new Talk("talk1", 60, TimeUnit.MINUTES));
        talks.add(new Talk("talk2", 30, TimeUnit.MINUTES));
        talks.add(new Talk("talk3", 10, TimeUnit.MINUTES));

        Conference componentUnderTest = new Conference(talks);
        List<Talk> actual = componentUnderTest.getTalksByTime(100);

        assertTrue(actual.size() == talks.size());
    }

    @Test
    public void shouldSchedulesOnlyFittingTalks() throws IOException {
        List<Talk> talks = new ArrayList<>();

        talks.add(new Talk("talk1", 60, TimeUnit.MINUTES));
        talks.add(new Talk("talk2", 30, TimeUnit.MINUTES));

        Conference componentUnderTest = new Conference(talks);

        List<Talk> actual = componentUnderTest.getTalksByTime(70);

        assertTrue(actual.size() == talks.size() - 1);
        assertTrue(actual.get(0).getTitle().equals("talk1"));
    }
}
