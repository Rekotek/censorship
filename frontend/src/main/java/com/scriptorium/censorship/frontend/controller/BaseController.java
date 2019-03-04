package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.BookService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BaseController {
    protected final BookService bookService;

    public BaseController(BookService bookService) {
        this.bookService = bookService;
    }

    protected List<Book> retrieveBookList(String searchBook, String publisher) {
        List<Book> bookList;
        if (StringUtils.isBlank(publisher)) {
            bookList = bookService.searchBookByTitle(searchBook.trim().toUpperCase());
        } else {
            bookList = bookService.searchBookByTitleAndPublisher(searchBook.trim().toUpperCase(), publisher.trim().toUpperCase());
        }
        return bookList;
    }

    protected String constructPublisher(String searchBook, String publisher) {
        if (StringUtils.isBlank(publisher) & (StringUtils.isBlank(searchBook))) {
            publisher = "СОФИЯ";
        }
        return publisher;
    }
}
