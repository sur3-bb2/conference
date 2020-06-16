import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class TrackTest {
    @Test()
    public void shouldGetCreated() {
        Track componentUnderTest = new Track();

        assertNotNull(componentUnderTest.getMorningSession());
        assertNotNull(componentUnderTest.getEveningSession());
    }
}
