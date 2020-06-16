import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConferenceManager {
    private Conference conference;
    private int networkStartTimeInMins = 420;

    public ConferenceManager(Conference conference) {
        this.conference = Optional.of(conference).get();
    }

    public List<Track> getTracks() {
        List<Track> tracks = new ArrayList<>();

        while (true) {
            Track track = new Track();
            Session morningSession = track.getMorningSession();
            Session eveningSession = track.getEveningSession();

            List<Talk> morningTalks = this.conference.getTalksByTime(morningSession.getDuration());
            List<Talk> eveningTalks = this.conference.getTalksByTime(eveningSession.getDuration());

            if(morningTalks == null && eveningTalks == null) {
                return tracks;
            }

            if(morningTalks != null) morningSession.addTalks(morningTalks);
            if(eveningTalks != null) eveningSession.addTalks(eveningTalks);

            tracks.add(track);
         }
    }

    public List<Track> getTracks(TrackInput input) {
        List<Track> tracks = new ArrayList<>();

        while (true) {
            Track track = new Track();
            int totalTalkTime = 0;

            for(int i = 0; i < input.getSessions().size(); i++) {
                Session session = input.getSessions().get(i);

                if(!session.isTalksAllowed()) {
                    track.addSession(session);
                    continue;
                }

                List<Talk> talks = this.conference.getTalksByTime(session.getDuration());

                if(talks != null && talks.size() > 0) {
                    Session scheduledSession = new Session();
                    scheduledSession.initialise(session.getName(), session.getStartTime(),
                                session.getEndTime(), session.getDuration(), session.isTalksAllowed());

                    scheduledSession.addTalks(talks);
                    track.addSession(scheduledSession);
                    totalTalkTime += scheduledSession.getTotalTalkTime();

                    if(i == input.getSessions().size() - 1) {
                        String startTime = "05:00 PM";

                        if(totalTalkTime > networkStartTimeInMins) {
                            startTime = TimeFormatHelper.fromMinutesToHHmm(networkStartTimeInMins + (networkStartTimeInMins - totalTalkTime));
                        }
                        scheduledSession.addTalk(new Talk(startTime + " Network Event", 0, java.util.concurrent.TimeUnit.MINUTES));
                    }
                } else {
                    return tracks;
                }
            }

            tracks.add(track);
        }
    }
}
