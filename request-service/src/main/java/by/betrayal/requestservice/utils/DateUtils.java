package by.betrayal.requestservice.utils;

import java.util.Calendar;
import java.util.TimeZone;

public abstract class DateUtils {

    public static Long getTicksFromUtcZone() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
    }
}
