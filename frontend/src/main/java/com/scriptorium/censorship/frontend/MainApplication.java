package com.scriptorium.censorship.frontend;

import com.scriptorium.censorship.frontend.service.AppSettingsService;
import com.scriptorium.censorship.frontend.service.BookService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-12.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class MainApplication implements CommandLineRunner {
    private static final Logger LOG = getLogger(MainApplication.class);

    @Autowired
    private AppSettingsService settingsService;

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        settingsService.loadProperties();
        if (settingsService.isNewDatabase()) {
            LOG.info("================== NEW DATABASE =================");
            bookService.fillDatabase();
        }
        LOG.info("Application started...");
    }
}
