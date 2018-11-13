package com.scriptorium.censorship.common.util;

import static java.lang.Integer.parseInt;

/**
 * Created by taras on 2018-11-12.
 */

public final class Converters {
    private Converters() { }

    public static int convertStringValue(String s) {
        int result;
        try {
            result = parseInt(s);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }
}
