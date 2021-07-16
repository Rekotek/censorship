package com.scriptorium.censorship.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
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

    public static String extractTargetFromUrl(String urlSource, String wordInsideTarget) throws IOException {
//        Document doc = Jsoup.connect(urlSource).get();
        Document doc = Jsoup.parse( new URL(urlSource).openStream(), "windows-1251", urlSource);
        Elements links = doc.select("a[href]");
        for (Element element : links) {
            Element anchor = element.getElementsContainingText(wordInsideTarget).first();
            if (anchor != null)
                return anchor.attr("href");
        }
        return null;
    }
}
