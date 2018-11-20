package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.AppSettingsService;
import com.scriptorium.censorship.frontend.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
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

    private void fillBaseAttributes(Model model, List<Book> bookList) {
        model.addAttribute("bookList", bookList);
        model.addAttribute("booksQuantity", bookService.getBookQuantity());
        model.addAttribute("lastFileUpdate", settingsService.getLastDownloadTime());
    }

    @GetMapping(value = "/")
    public String redirectIndex(Model model) {
        List<Book> bookList = bookService.searchBookByTitleAndPublisher("", "София");
        fillBaseAttributes(model, bookList);
        model.addAttribute("publisher", "София");
        return "index";
    }

    @GetMapping(value = "/index.html")
    public String indexPage() {
        return "redirect:/";
    }

    @PostMapping(value = "/")
    public String searchPage(@RequestParam("searchBook") String searchBook,
                             @RequestParam("publisher") String publisher,
                             Model model) {
        List<Book> bookList;
        if (StringUtils.isBlank(publisher)) {
            if (StringUtils.isBlank(searchBook)) {
                bookList = Collections.emptyList();
                model.addAttribute("error", "Слишком большой список!!!");
            } else {
                bookList = bookService.searchBookByTitle(searchBook);
            }
        } else {
            bookList = bookService.searchBookByTitleAndPublisher(searchBook, publisher);
        }

        fillBaseAttributes(model, bookList);
        model.addAttribute("searchBook", searchBook);
        model.addAttribute("publisher", publisher);
        return "index";

    }


}



