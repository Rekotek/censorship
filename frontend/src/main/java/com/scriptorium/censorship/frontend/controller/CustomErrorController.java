package com.scriptorium.censorship.frontend.controller;

import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger log = getLogger(CustomErrorController.class);

    @RequestMapping("${server.error.path}")
    public String redirectErrors(HttpServletRequest request) {
        log.warn("Incorrect request from <{}> for {}", request.getRemoteAddr(), request.getRequestURI());
        return "redirect:/";
    }

    public String getErrorPath() {
        return null;
    }
}
