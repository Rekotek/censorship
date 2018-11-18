package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.common.model.BookDto;
import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.exception.CensorSiteNotWorkingException;
import com.scriptorium.censorship.frontend.repository.BookRepository;
import com.scriptorium.censorship.parser.boundary.BookListLoader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scriptorium.censorship.parser.boundary.BookListLoader.getFileSize;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-13.
 */
@Service
public final class BookService {
    private static final Logger LOG = getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AppSettingsService settingsService;

    @Value("${censorship.url}")
    private String urlAddress;

    public BookService(BookRepository bookRepository, AppSettingsService settingsService) {
        this.bookRepository = bookRepository;
        this.settingsService = settingsService;
    }

    public void fillDatabase() throws CensorSiteNotWorkingException {
        LOG.info("Run fillDatabase()");
        long newFileSize = getFileSize(urlAddress);
        if (newFileSize == 0) {
            LOG.error("Cannot load file.");
            throw new CensorSiteNotWorkingException("Невозможно получить файл.");
        }
        if (newFileSize != settingsService.getLastFileSize()) {
            List<BookDto> bookDtoList = BookListLoader.loadFileFromUrl(urlAddress);
            List<Book> books = bookDtoList.stream().map(this::createBookEntity).collect(toList());
            LOG.debug("Converted {} books into entities", books.size());
            bookRepository.deleteAll();
            bookRepository.saveAll(books);
            settingsService.saveProperties(newFileSize);
        }
    }

    public long getBookQuantity() {
        return bookRepository.count();
    }

    public List<Book> getInitialBookList() {
        return bookRepository.getInitialBookList();
    }
    private Book createBookEntity(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPublisher(bookDto.getPublisher());
        book.setQuantity(bookDto.getQuantity());
        book.setRuTitle(bookDto.getRuTitle());
        book.setUaTitle(bookDto.getUaTitle());
        book.setTradeCompany(bookDto.getTradeCompany());
        book.setYearOfPublish(bookDto.getYearOfPublish());
        return book;
    }
}
