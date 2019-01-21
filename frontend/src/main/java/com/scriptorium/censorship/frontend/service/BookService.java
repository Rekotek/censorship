package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.common.model.BookDto;
import com.scriptorium.censorship.common.model.ContentParams;
import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.exception.CensorSiteNotWorkingException;
import com.scriptorium.censorship.frontend.repository.BookRepository;
import com.scriptorium.censorship.parser.boundary.BookListLoader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.scriptorium.censorship.parser.boundary.BookListLoader.loadContentParams;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by taras on 2018-11-13.
 */
@Service
public class BookService {
    private static final Logger LOG = getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AppSettingsService settingsService;
    private final CacheManager cacheManager;

    @Value("${censorship.url}")
    private String urlAddress;

    public BookService(BookRepository bookRepository, AppSettingsService settingsService, CacheManager cacheManager) {
        this.bookRepository = bookRepository;
        this.settingsService = settingsService;
        this.cacheManager = cacheManager;
    }

    private String prepareForSql(String in) {
        return "%" + in
                .replaceAll("\\s{2,}", " ")
                .replace(" ", "%")
                .replaceAll("[И,І,Е,Э,Є,Ё,Ы]", "_")
                 + "%";
    }

    public void fillDatabase() throws CensorSiteNotWorkingException {
        LOG.info("Run fillDatabase()");

        ContentParams newContentParams = loadContentParams(urlAddress);
        long newFileSize = newContentParams.getFileSize();
        if (newFileSize == 0) {
            LOG.error("Cannot load file.");
            throw new CensorSiteNotWorkingException("Файл загрузился с нулевой длиной");
        }
        if (settingsService.shouldReloadData(newContentParams)) {
            final List<BookDto> bookDtoList = BookListLoader.loadDataFromUrl(urlAddress, newContentParams.isXlsx());
            final List<Book> books = bookDtoList.stream().map(this::createBookEntity).collect(toList());
            LOG.info("Converted {} books into entities", books.size());
            cacheManager.getCache("books").clear();
            bookRepository.deleteAll();
            bookRepository.saveAll(books);
            settingsService.saveProperties(newContentParams);
        }
    }

    public long getBookQuantity() {
        return bookRepository.count();
    }

    public List<Book> searchBookByTitle(String title) {
        String parsedTitle = prepareForSql(title);
        LOG.info("For Title only: '{}'", title);

        return bookRepository.searchBookByTitle(parsedTitle);
    }

    @Cacheable(value = "books", key = "{#title, #publisher}")
    public List<Book> searchBookByTitleAndPublisher(String title, String publisher) {
        String parsedTitle = prepareForSql(title);
        String preparedPublisher = prepareForSql(publisher);

        LOG.debug("For Title: '{}' and Publisher: '{}'", title, publisher);

        return bookRepository.searchBookByTitleAndPublisher(parsedTitle, preparedPublisher);
    }

    private Book createBookEntity(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setAuthorUpper(null == bookDto.getAuthor() ? "" : bookDto.getAuthor().toUpperCase());
        book.setIsbn(bookDto.getIsbn());
        book.setPublisher(bookDto.getPublisher());
        book.setPublisherUpper(null == bookDto.getPublisher() ? "" : bookDto.getPublisher().toUpperCase());
        book.setQuantity(bookDto.getQuantity());
        book.setRuTitle(bookDto.getRuTitle());
        book.setRuTitleUpper(null == bookDto.getRuTitle() ? "" : bookDto.getRuTitle().toUpperCase());
        book.setUaTitle(bookDto.getUaTitle());
        book.setTradeCompany(bookDto.getTradeCompany());
        book.setYearOfPublish(bookDto.getYearOfPublish());
        book.setDocumentNum(bookDto.getDocumentNum());
        book.setDocumentDate(bookDto.getDocumentStartDate());
        return book;
    }
}
