package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.service.LogReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShowLogController {
    private final LogReaderService logReaderService;

    public ShowLogController(LogReaderService logReaderService) {
        this.logReaderService = logReaderService;
    }

    @GetMapping("/logs")
    public String showLogInfo(@RequestParam(value = "lines", required = false) Integer lines, Model model) {
        if (null == lines) {
            lines = 10;
        }
        if (lines > LogReaderService.MAX_LINES) {
            lines = LogReaderService.MAX_LINES;
        }

        List<String> logList = logReaderService.tailLog(lines);
        model.addAttribute("log", logList);
        model.addAttribute("lines", lines);
        return "logs";
    }
}
