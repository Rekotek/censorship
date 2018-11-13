package com.scriptorium.censorship.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by taras on 2018-11-12.
 */
@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String redirectIndex() {
        return "redirect:/index.html";
    }

    @GetMapping(value = "/index.html")
    public String indexPage() {
        return "index";
    }
}



