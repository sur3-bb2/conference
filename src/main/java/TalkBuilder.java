import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TalkBuilder {
    public static final String Lightning = "lightning";
    public static final String LightningDefaultValue = "5";
    private String data;
    private Parser parser;
    private boolean sortDesc = true;
    private Comparator<Talk> comparator;

    private TalkBuilder() { }

    public static TalkBuilder builder() {
        return new TalkBuilder();
    }

    public TalkBuilder from(String data) {
        this.data = data;

        return this;
    }

    public TalkBuilder using(Parser parser) {
        this.parser = parser;

        return this;
    }

    public TalkBuilder sort(Comparator<Talk> comparator, boolean desc) {
        this.comparator = comparator;
        this.sortDesc = desc;

        return this;
    }

    public List<Talk> build() {
        Map<String, String> parsedContents = Optional.of(this.parser.getContents(this.data)).get();

        List<Talk> talks = parsedContents.keySet()
                .stream()
                .map(key -> {
                    String time = Optional.of(parsedContents.get(key))
                                        .map(k -> k.equals(Lightning) ? LightningDefaultValue : k)
                                        .get()
                                        .replaceAll("[^0-9]*", "");

                    return new Talk(key, Integer.parseInt(time), TimeUnit.MINUTES);
                }).collect(Collectors.toList());

        if(this.sortDesc) {
            talks.sort(this.comparator.reversed());
        } else {
            talks.sort(this.comparator);
        }

        return talks;
    }
}
