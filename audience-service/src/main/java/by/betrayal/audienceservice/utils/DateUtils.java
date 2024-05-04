package by.betrayal.audienceservice.utils;

import java.util.Calendar;
import java.util.TimeZone;

public abstract class DateUtils {


    public static Long getUtcTicks() {
        return Calendar.getInstance(getUtc()).getTimeInMillis();
    }

    private static TimeZone getUtc() {
        return TimeZone.getTimeZone("UTC");
    }
}
