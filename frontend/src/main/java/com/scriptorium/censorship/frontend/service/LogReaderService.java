package com.scriptorium.censorship.frontend.service;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogReaderService {
    public static final int MAX_LINES = 50;
    @Value("${CENSOR_APP_ROOT}/log/censorship.log")
    private String logFile;

    public List<String> tailLog(int linesToShow) {
        if (linesToShow > MAX_LINES) {
            linesToShow = MAX_LINES;
        }
        List<String> resultList = new ArrayList<>(linesToShow);
        File file = new File(logFile);
        try {
            try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {
                int count = 0;
                String log_line;
                while ((log_line = reader.readLine()) != null && count < linesToShow) {
                    resultList.add(log_line);
                    count++;
                }
            }
        } catch (IOException e) {
            resultList.add("ERROR when reading log file!");
        }
        return resultList;
    }
}
