package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.common.model.ContentParams;
import com.scriptorium.censorship.common.util.Converters;
import com.scriptorium.censorship.frontend.repository.BookRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
    private static final String KEY_LAST_QUANTITY = "lastQuantity";
    private static final String KEY_IS_XLSX = "isXlsx";

    private final BookRepository bookRepository;

    public AppSettingsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Value("${censorship.db-dir}")
    private String dbPath;

    private ContentParams lastSavedParams = new ContentParams();

    public boolean isNewDatabase() {
        long recordQuantity = bookRepository.count();
        return (lastSavedParams.getFileSize() == 0 || recordQuantity == 0);
    }

    public boolean shouldReloadData(ContentParams newContentParams) {
        return (!lastSavedParams.equals(newContentParams));
    }

    public void loadProperties() {
        String propertyFile = dbPath + File.separator + APP_PROPERTIES_FILE;
        LOG.info("Trying to load properties from {}", propertyFile);
        Properties prop = new Properties();
        try (InputStream fis = new FileInputStream(propertyFile)) {
            prop.load(fis);
            try {
                lastSavedParams.setFileSize(Long.parseLong(prop.getProperty(KEY_LAST_FILE_SIZE, "0")));
                lastSavedParams.setFileSize(Integer.parseInt(prop.getProperty(KEY_LAST_QUANTITY, "30000")));
                lastSavedParams.setLastModified(LocalDateTime.parse(prop.getProperty(KEY_LAST_DOWNLOAD_TIME)));
                lastSavedParams.setXlsx(Boolean.parseBoolean(prop.getProperty(KEY_IS_XLSX)));
            } catch (NumberFormatException e) {
                LOG.error("Parsing property error: {}", e.getMessage());
            }
        } catch (IOException e) {
            LOG.info("There is no properties file");
        }
    }

    public void applyProperties(ContentParams contentParams) {
        lastSavedParams.setFileSize(contentParams.getFileSize());
        lastSavedParams.setLastModified(contentParams.getLastModified());
        lastSavedParams.setXlsx(contentParams.isXlsx());
        lastSavedParams.setQuantity(contentParams.getQuantity());
        store();
    }

    private void store() {
        Properties prop = new Properties();
        try (OutputStream fos = new FileOutputStream(dbPath + File.separator + APP_PROPERTIES_FILE)) {
            prop.setProperty(KEY_LAST_FILE_SIZE, String.valueOf(lastSavedParams.getFileSize()));
            prop.setProperty(KEY_LAST_DOWNLOAD_TIME, lastSavedParams.getLastModified().format(ISO_LOCAL_DATE_TIME));
            prop.setProperty(KEY_IS_XLSX, String.valueOf(lastSavedParams.isXlsx()));
            prop.setProperty(KEY_LAST_QUANTITY, String.valueOf(lastSavedParams.getQuantity()));
            prop.store(fos, null);
        } catch (IOException e) {
            LOG.error("Cannot write properties file: {}", e.getMessage());
        }
    }

    public int getLastQuantity() {
        return lastSavedParams.getQuantity();
    }

    public void setLastQuantity(int lastQuantity) {
        lastSavedParams.setQuantity(lastQuantity);
        store();
    }
    public String getLastDownloadTime() {
        return Converters.toString(lastSavedParams.getLastModified());
    }
}
