import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Session {
    @JsonProperty("name")
    private String name;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("talksAllowed")
    private boolean talksAllowed = true;

    @JsonIgnore
    private int lastTalkEndTime = -1;

    @JsonIgnore
    private List<Talk> talks = new ArrayList<>();

    private Session(String name, String startTime, String endTime, int duration, boolean talksAllowed) {
        initialise(name, startTime, endTime, duration, talksAllowed);
    }

    public Session() { }

    public void initialise(String name, String startTime, String endTime, int duration, boolean talksAllowed) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setDuration(duration);
        this.talksAllowed = talksAllowed;
    }

    public void addTalk(Talk talk) {
        if(this.getTotalTalkTime() <= 0) {
            talk.setStartTime(this.startTime);
        } else {
            talk.setStartTime(TimeFormatHelper.fromMinutesToHHmm(this.getTotalTalkTime()));
        }

        this.talks.add(talk);
        this.lastTalkEndTime = talk.getDuration();
    }

    @JsonIgnore
    public void addTalks(List<Talk> talks) {
        talks.forEach(talk -> this.addTalk(talk));
    }

    public List<Talk> getAllTalks() {
        return this.talks;
    }

    public String toString() {
        return name + " session starts at " + startTime  + " and ends at " + endTime;
    }

    public static Session createMorningSession() {
        return new Session("Morning", "09:00 am", "12:00 pm", 3 * 60, true);
    }

    public static Session createEveningSession() {
        return new Session("Evening", "01:00 pm", "05:00 pm", 4 * 60, true);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public boolean isTalksAllowed() {
        return talksAllowed;
    }

    public int getTotalTalkTime() {
        if(!isTalksAllowed()) return this.getDuration();

        int totalTalkTime = 0;

        for(Talk talk : this.getAllTalks()) {
            totalTalkTime += talk.getDuration();
        }

        return totalTalkTime;
    }
}
