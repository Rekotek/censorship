package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.frontend.repository.BookRepository;
import com.scriptorium.censorship.frontend.util.DateTimeUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-13.
 */

@Service
public final class AppSettingsService {
    private static final Logger LOG = getLogger(AppSettingsService.class);

    private static final String APP_PROPERTIES_FILE = "app.properties";
    private static final String KEY_LAST_DOWNLOAD_TIME = "lastDownloadTime";
    private static final String KEY_LAST_FILE_SIZE = "lastFileSize";

    private final BookRepository bookRepository;

    public AppSettingsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Value("${censorship.db-dir}")
    private String dbPath;

    private long lastFileSize = 0;
    private String lastDownloadTime;

    public boolean isNewDatabase() {
        long recordQuantity = bookRepository.count();
        return (lastFileSize == 0) || (lastDownloadTime == null) || (recordQuantity == 0);
    }

    public void loadProperties() {
        String propertyFile = dbPath + File.separator + APP_PROPERTIES_FILE;
        LOG.debug("Load property from rile {}", propertyFile);
        Properties prop = new Properties();
        try (InputStream fis = new FileInputStream(propertyFile)) {
            prop.load(fis);
            try {
                this.lastFileSize = Long.parseLong(prop.getProperty(KEY_LAST_FILE_SIZE, "0"));
            } catch (NumberFormatException e) {
                LOG.error("Incorrect lastFileSize property: {}", e.getMessage());
            }
            String propDateTime = prop.getProperty(KEY_LAST_DOWNLOAD_TIME);
            if (null != propDateTime) {
                this.lastDownloadTime = propDateTime;
            }
        } catch (IOException e) {
            LOG.info("There is no properties file");
        }
    }

    void saveProperties(long newFileSize) {
        Properties prop = new Properties();
        try (OutputStream fos = new FileOutputStream(dbPath + File.separator + APP_PROPERTIES_FILE)) {
            prop.setProperty(KEY_LAST_FILE_SIZE, String.valueOf(newFileSize));
            LocalDateTime now = LocalDateTime.now();
            prop.setProperty(KEY_LAST_DOWNLOAD_TIME, DateTimeUtil.toString(now));
            prop.store(fos, null);
            this.lastFileSize = newFileSize;
        } catch (IOException e) {
            LOG.error("Cannot write properties file: {}", e.getMessage());
        }
    }

    public String getLastDownloadTime() {
        return lastDownloadTime;
    }

    long getLastFileSize() {
        return lastFileSize;
    }
}
