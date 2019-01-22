package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.AppSettingsService;
import com.scriptorium.censorship.frontend.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-12.
 */
@Controller
public class IndexController {
    private static final Logger LOG = getLogger(IndexController.class);

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

    @GetMapping(value = "/index.html")
    public String indexPage() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String searchBook(@RequestParam(value = "searchBook", required = false) String searchBook,
                             @RequestParam(value = "publisher", required = false) String publisher,
                             HttpServletRequest request,
                             Model model) {
        List<Book> bookList;
        LOG.debug("From <{}> with '{}', publisher '{}'", request.getRemoteAddr(), searchBook, publisher);
        if (StringUtils.isBlank(publisher)) {
            if (StringUtils.isBlank(searchBook)) {
                publisher = "СОФИЯ";
                bookList = bookService.searchBookByTitleAndPublisher("", publisher);
            } else {
                bookList = bookService.searchBookByTitle(searchBook.trim().toUpperCase());
            }
        } else {
            bookList = bookService.searchBookByTitleAndPublisher(searchBook.trim().toUpperCase(), publisher.trim().toUpperCase());
        }

        fillBaseAttributes(model, bookList);
        model.addAttribute("searchBook", searchBook);
        model.addAttribute("publisher", publisher);
        return "index";
    }
}



