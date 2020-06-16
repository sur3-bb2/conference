import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class TalkBuilderTest {
    private String fileContents;
    private List<String> fileContentLines;

    @Before
    public void setup() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("input.txt");

        fileContents = new String(Files.readAllBytes(Paths.get(url.getFile())));
        fileContentLines = Files.readAllLines(Paths.get(url.getFile()));
    }

    @Test()
    public void shouldReturnInstanceWithProps() {
        List<Talk> talks = TalkBuilder.builder()
                .from(fileContents)
                .using(new RightToLeftParser())
                .sort(Comparator.comparing(Talk::getDuration), false)
                .build();

        assertTrue(talks.size() == 19);

        boolean allTitleMatched = fileContentLines
                .stream()
                .filter(f -> !f.endsWith(TalkBuilder.Lightning))
                .allMatch(i -> talks
                        .stream()
                        .filter(f -> f.getDuration() != Integer.valueOf(TalkBuilder.LightningDefaultValue))
                        .anyMatch(t -> i.startsWith(t.getTitle())
                                    && i.endsWith(String.valueOf(t.getDuration()) + "min"))
                );

        assertTrue(allTitleMatched);

        talks.forEach(talk -> {
            assertTrue(talk.getDurationUnit().equals(TimeUnit.MINUTES));
        });
    }

    @Test()
    public void shouldReturnSortedTalks() {
        List<Talk> talks = TalkBuilder.builder()
                .from(fileContents)
                .using(new RightToLeftParser())
                .sort(Comparator.comparing(Talk::getDuration), true)
                .build();

        final Talk[] prev = {talks.get(0)};

        talks.forEach(talk -> {
            if(prev[0] != talk) {
                assertTrue(prev[0].getDuration() >= talk.getDuration());
            }

            prev[0] = talk;
        });
    }
}