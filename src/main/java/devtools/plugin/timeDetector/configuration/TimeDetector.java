package devtools.plugin.timeDetector.configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeDetector {
    public static final LocalTime startTime = LocalTime.now();
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String getId() {
        return TimeDetector.class.getName();
    }
}
