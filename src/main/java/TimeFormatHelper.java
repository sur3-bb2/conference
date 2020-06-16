public class TimeFormatHelper {
    public static String fromMinutesToHHmm(int minutes) {
        String startTime = "00:00";
        int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
        int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));

        return h + ":" + m;
    }
}
