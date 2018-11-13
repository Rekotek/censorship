package com.scriptorium.censorship.parser.boundary;

import com.scriptorium.censorship.common.model.BookDto;
import com.scriptorium.censorship.parser.util.BookXlsMapper;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-12.
 */

public final class BookListLoader {
    private static final Logger LOG = getLogger(BookListLoader.class);

    private static final int ROWS_TO_OMIT = 2;

    private BookListLoader() {
    }

    //TODO: Move this to backend's Service Layer
    public static boolean isNewFileOnServer(String urlAddress, long previousFileSize) {
        long newFileSize = getFileSize(urlAddress);
        if (newFileSize == 0) {
            return false;
        } else {
            return newFileSize != previousFileSize;
        }
    }

    public static long getFileSize(String urlAddress) {
        try {
            URL urlObject = new URL(urlAddress);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("HEAD");
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                LOG.error("Response code is: {}", status);
                return 0;
            }
            return urlConnection.getContentLengthLong();
        } catch (MalformedURLException e) {
            LOG.error("The URL is not correct: {}", urlAddress);
            return 0;
        } catch (IOException e) {
            LOG.error("Error establish connection: {}", e.getMessage());
            return 0;
        }
    }

    public static List<BookDto> loadFileFromUrl(String urlAddress) {
        return loadFileFromUrl(urlAddress, ROWS_TO_OMIT);
    }

    public static List<BookDto> loadFileFromUrl(String urlAddress, int rowsToOmit) {
        LOG.debug("Trying to get file from URL = {}", urlAddress);
        URL urlObject;
        URLConnection urlConnection;
        try {
            urlObject = new URL(urlAddress);
            urlConnection = urlObject.openConnection();
        } catch (MalformedURLException e) {
            LOG.error("The URL is not correct: {}", urlAddress);
            return Collections.emptyList();
        } catch (IOException e) {
            LOG.error("Error establish connection: {}", e.getMessage());
            return Collections.emptyList();
        }

        try (InputStream stream = urlConnection.getInputStream()) {
            List<BookDto> bookDtoList;
            bookDtoList = BookXlsMapper.parseXlsStream(stream, rowsToOmit);
            return bookDtoList;
        } catch (IOException e) {
            LOG.error("Error loading file from site: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
