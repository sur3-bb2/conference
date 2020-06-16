import java.util.*;

public class Track {
    private Session morningSession;
    private Session eveningSession;

    private ArrayList<Session> sessions = new ArrayList<>();

    public Track() {
        this.morningSession = Session.createMorningSession();
        this.eveningSession = Session.createEveningSession();
    }

    public Session getMorningSession() {
        return morningSession;
    }

    public Session getEveningSession() {
        return eveningSession;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSession() {
        return this.sessions;
    }
}
