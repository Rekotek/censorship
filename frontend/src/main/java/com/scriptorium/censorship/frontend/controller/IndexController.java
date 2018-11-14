package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.AppSettingsService;
import com.scriptorium.censorship.frontend.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        long bookQuantity = bookService.getBookQuantity();
        List<Book> bookList = bookService.getInitialBookList();
        model.addAttribute("bookList", bookList);
        model.addAttribute("booksQuantity", bookQuantity);
        model.addAttribute("lastFileUpdate", settingsService.getLastDownloadTime());
        return "index";
    }
}



