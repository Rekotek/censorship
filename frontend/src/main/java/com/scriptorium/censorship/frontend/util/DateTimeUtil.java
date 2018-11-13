package com.scriptorium.censorship.frontend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by taras on 2018-11-13.
 */

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
