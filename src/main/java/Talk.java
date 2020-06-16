import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Talk {
    private String title;
    private String startTime;
    private int duration;
    private TimeUnit durationUnit;

    public Talk(String title, int duration, TimeUnit durationUnit) {
        this.title = Optional.of(title).get();
        this.duration = Optional.of(duration).get();
        this.durationUnit = Optional.of(durationUnit).get();
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public TimeUnit getDurationUnit() {
        return durationUnit;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String toString() {
        return String.format("Start Time : %s, Title : %s, Duration : %d %s",
                getStartTime(),
                getTitle(),
                getDuration(),
                getDurationUnit().toString());
    }
}
