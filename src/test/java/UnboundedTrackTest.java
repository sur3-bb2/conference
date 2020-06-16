import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UnboundedTrackTest {
    @Test
    public void shouldBeInitializedByJson() throws IOException {
        TrackInput componentToTest = new TrackInput();

        assertNotNull(componentToTest.getSessions());
        assertTrue(componentToTest.getSessions().size() == 3);

        componentToTest.getSessions().forEach( session -> System.out.println(session.toString()));
    }
}
