package com.scriptorium.censorship.frontend.controller;

import com.scriptorium.censorship.frontend.entity.Book;
import com.scriptorium.censorship.frontend.service.BookService;
import com.scriptorium.censorship.frontend.service.ExcelCreatorService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class DownloadController extends BaseController {
    private static final Logger LOG = getLogger(DownloadController.class);

    private final ExcelCreatorService excelCreatorService;

    public DownloadController(BookService bookService, ExcelCreatorService excelCreatorService) {
        super(bookService);
        this.excelCreatorService = excelCreatorService;
    }

    @GetMapping(value = "/download", produces = "application/vnd.ms-excel")
    @ResponseBody
    public void getXls(@RequestParam(value = "searchBook", required = false) String requestBook,
                       @RequestParam(value = "publisher", required = false) String requestPublisher,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        LOG.debug("From <{}> with '{}', publisher '{}'", request.getRemoteAddr(), requestBook, requestPublisher);

        var publisher = constructPublisher(requestBook, requestPublisher);
        List<Book> bookList = retrieveBookList(requestBook, publisher);
        response.setHeader("Content-Disposition","attachment; filename=Book.xlsx");

        ByteArrayOutputStream workbookStream = excelCreatorService.buildWorkbook(bookList);
        try {
            StreamUtils.copy(workbookStream.toByteArray(), response.getOutputStream());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            response.sendRedirect("/");
        }
    }
}
