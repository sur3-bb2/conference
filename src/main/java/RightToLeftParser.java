import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RightToLeftParser implements Parser {
    private final static String DELIMITER = " ";

    public Map<String, String> getContents(String contents) {
        if(contents == null || contents.isEmpty()) {
            return null;
        }

        String[] lines = contents.split(System.getProperty("line.separator"));
        HashMap<String, String> parsedLines = new HashMap<>();

        Arrays.asList(lines)
                .stream()
                .forEach(line -> {
                    int delimiterIndex = line.lastIndexOf(DELIMITER);

                    String value = line.substring(delimiterIndex).trim();
                    String key = line.substring(0, delimiterIndex);

                    parsedLines.put(key, value);
                });

        return parsedLines;
    }
}
