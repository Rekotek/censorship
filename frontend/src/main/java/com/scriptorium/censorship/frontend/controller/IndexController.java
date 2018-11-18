package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.AppSettingsService;
import com.scriptorium.censorship.frontend.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by taras on 2018-11-12.
 */
@Controller
public class IndexController {
    private final BookService bookService;
    private final AppSettingsService settingsService;

    public IndexController(BookService bookService, AppSettingsService settingsService) {
        this.bookService = bookService;
        this.settingsService = settingsService;
    }

    @GetMapping(value = "/")
    public String redirectIndex() {
        return "redirect:/index.html";
    }

    @GetMapping(value = "/index.html")
    public String indexPage(Model model) {
        List<Book> bookList = bookService.getInitialBookList();
        fillBaseAttributes(model, bookList);
        return "index";
    }

    private void fillBaseAttributes(Model model, List<Book> bookList) {
        model.addAttribute("bookList", bookList);
        model.addAttribute("booksQuantity", bookService.getBookQuantity());
        model.addAttribute("lastFileUpdate", settingsService.getLastDownloadTime());
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String searchPage(HttpServletRequest request,
                             Model model) {
        List<Book> bookList = bookService.getInitialBookList();
        fillBaseAttributes(model, bookList);
        return "index";

    }


}



