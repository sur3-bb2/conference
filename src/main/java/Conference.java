import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Conference {
    private List<Talk> talks;

    public Conference(List<Talk> talks) {
        this.talks = new ArrayList<>(Optional.of(talks).get());
    }

    /**
     * gets all talks that can fit into window
     * @param window - time in minutes
     * @return
     */
    public List<Talk> getTalksByTime(int window) {
        List<Talk> selected = new ArrayList<>();
        AtomicInteger elapsedWindow = new AtomicInteger();
        Iterator<Talk> iterator = this.talks.iterator();

        while(iterator.hasNext()) {
            Talk talk = iterator.next();

            System.out.println("Elapsed : " + elapsedWindow.get() + ", Window : " + window + " Duration : " + talk.getDuration());

            if(talk.getDuration() + elapsedWindow.get() <= window) {
                selected.add(talk);
                iterator.remove();
                elapsedWindow.addAndGet(talk.getDuration());
            }
        }

        return selected.size() == 0 ? null : selected;
    }
}
