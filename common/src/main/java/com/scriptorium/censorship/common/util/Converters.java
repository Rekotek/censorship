package com.scriptorium.censorship.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.Integer.parseInt;

/**
 * Created by taras on 2018-11-12.
 */

public final class Converters {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("d MMM yyyy HH:mm", new Locale("ru"));

    private Converters() { }

    public static int toInt(String s) {
        int result;
        try {
            result = parseInt(s);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

}
