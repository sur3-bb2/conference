import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RightToLeftParserTest {
    @Test()
    public void shouldReturnNullForNullInput() {
        assertNull(new RightToLeftParser().getContents(null));
    }

    @Test()
    public void shouldReturnNullForEmptyInput() {
        assertNull(new RightToLeftParser().getContents(""));
    }

    @Test()
    public void shouldReturnParsedData() {
        String test = "Writing Fast Tests Against Enterprise Rails 60min";

        Map<String, String> actual = new RightToLeftParser().getContents(test);

        assertTrue(actual.size() == 1);
        assertTrue(actual.keySet().stream().findFirst().get().equals("Writing Fast Tests Against Enterprise Rails"));
        assertTrue(actual.values().stream().findFirst().get().equals("60min"));
    }

    @Test()
    public void shouldParseDataWithLightningInKey() {
        String test = "Writing Fast Tests Against Enterprise lightning Rails lightning";

        Map<String, String> actual = new RightToLeftParser().getContents(test);

        assertTrue(actual.size() == 1);
        assertTrue(actual.keySet().stream().findFirst().get().equals("Writing Fast Tests Against Enterprise lightning Rails"));
        assertTrue(actual.values().stream().findFirst().get().equals("lightning"));
    }

    @Test()
    public void shouldParseMultipleLines() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("input.txt");
        String test = new String(Files.readAllBytes(Paths.get(url.getFile())));

        Map<String, String> actual = new RightToLeftParser().getContents(test);

        List<String> expected = Arrays.asList(test.split(System.getProperty("line.separator")));
        assertTrue(actual.size() == test.split(System.getProperty("line.separator")).length);

        actual.keySet().stream().forEach(key -> {
            assertTrue(expected.contains(key + " " + actual.get(key)));
        });
    }
}
