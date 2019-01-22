package com.scriptorium.censorship.parser.boundary;

import com.scriptorium.censorship.common.model.BookDto;
import com.scriptorium.censorship.common.model.ContentParams;
import com.scriptorium.censorship.common.model.ContentParams.ContentParamsBuilder;
import com.scriptorium.censorship.parser.util.BookXlsMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
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

    public static ContentParams loadContentParams(String urlAddress) {
        try {
            URL urlObject = new URL(urlAddress);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("HEAD");
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                LOG.error("Response code is: {}", status);
                return null;
            }

            ContentParamsBuilder contentParamsBuilder = ContentParams.builder();
            String contentName = urlConnection.getHeaderField("Content-Disposition");
            if (contentName.endsWith("xlsx")) {
                LOG.debug("Source file is 'xlsx'");
                contentParamsBuilder.xlsx(true);
            } else if (contentName.endsWith("xls")) {
                contentParamsBuilder.xlsx(false);
                LOG.debug("Source file is 'xls'");
            } else {
                LOG.error("Who knows what the extension of this file!");
                return null;
            }
            contentParamsBuilder.lastModified(LocalDateTime.now())
                    .fileSize(urlConnection.getContentLengthLong());
            return contentParamsBuilder.build();
        } catch (MalformedURLException e) {
            LOG.error("The URL is not correct: {}", urlAddress);
            return null;
        } catch (IOException e) {
            LOG.error("Error establish connection: {}", e.getMessage());
            return null;
        }
    }

    public static List<BookDto> loadDataFromUrl(String urlAddress, boolean expectXlsx) {
        return loadDataFromUrl(urlAddress, ROWS_TO_OMIT, expectXlsx);
    }

    public static List<BookDto> loadDataFromUrl(String urlAddress, int rowsToOmit, boolean expectXlsx) {
        LOG.debug("Trying to get file from censor's site");
        URL urlObject;
        try {
            urlObject = new URL(urlAddress);
        } catch (MalformedURLException e) {
            LOG.error("The URL is not correct: {}", urlAddress);
            return Collections.emptyList();
        }

        File tempFile = null;
        try {
            tempFile = File.createTempFile("loadedGoods-", "-tmp");
            FileUtils.copyURLToFile(urlObject, tempFile, 5000, 120000);
            LOG.info("File successfully saved, type is {}", expectXlsx ? "xlsx" : "xls");
            List<BookDto> bookDtoList;
            if (expectXlsx) {
                bookDtoList = BookXlsMapper.parseXlsxFile(tempFile, rowsToOmit);
            } else {
                bookDtoList = BookXlsMapper.parseXlsFile(tempFile, rowsToOmit);
            }
            return bookDtoList;
        } catch (IOException e) {
            LOG.error("Error loading or parsing file: {}", e.getMessage());
            return Collections.emptyList();
        } finally {
            if (tempFile != null) {
                if (!tempFile.delete()) {
                    LOG.error("Cannot delete temporary file!");
                }
            }
        }

    }
}
