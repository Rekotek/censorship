package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.frontend.exception.CensorSiteNotWorkingException;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ScheduleService {
    private static final Logger LOG = getLogger(ScheduleService.class);

    private final BookService bookService;

    public ScheduleService(BookService bookService) {
        this.bookService = bookService;
    }

//    @Scheduled(cron = "*/30 * * * * *")
    @Scheduled(cron = "24 12 9-17 * * ?")
    public void runLoadDatabase() {
        try {
            bookService.fillDatabase();
        } catch (CensorSiteNotWorkingException e) {
            LOG.warn("Censor site unavailable: {}", e.getMessage());
        }
    }
}
