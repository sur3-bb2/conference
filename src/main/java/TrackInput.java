import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TrackInput {
    private List<Session> sessions;

    public TrackInput() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("TrackInput.json");
        String data = new String(Files.readAllBytes(Paths.get(url.getFile())));
        Session[] sessionsInput = new ObjectMapper().readValue(data, Session[].class);

        this.setSessions(Arrays.asList(sessionsInput));
    }

    public List<Session> getSessions() {
        return this.sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
