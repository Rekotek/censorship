package com.scriptorium.censorship.parser.boundary;

import com.scriptorium.censorship.common.model.BookDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by taras on 2018-11-12.
 */

public class BookDtoListLoaderTest {
    private static final String URL_CENSOR_FILE = "http://comin.kmu.gov.ua/document/134477/Goods.xls";
    private static final long LAST_SIZE = 11385856;

    @Test
    public void loadFileFromUrl() {
        List<BookDto> bookDtos = BookListLoader.loadFileFromUrl(URL_CENSOR_FILE);
        Assert.assertTrue(bookDtos.size() > 0);
        System.out.println("==============First 3 books=============");
        System.out.println(bookDtos.get(0));
        System.out.println(bookDtos.get(1));
        System.out.println(bookDtos.get(2));
        System.out.println("==============Last 3 books=============");
        int lastIndex = bookDtos.size() - 5000;
        System.out.println(bookDtos.get(lastIndex));
        System.out.println(bookDtos.get(lastIndex - 1));
        System.out.println(bookDtos.get(lastIndex - 2));
    }

    @Test
    public void getFileSize() {
        long fileSize = BookListLoader.getFileSize(URL_CENSOR_FILE);
        Assert.assertTrue(fileSize > 0);
        System.out.println(fileSize);
    }

    @Test
    public void isFileNew() {
        boolean isNew = BookListLoader.isNewFileOnServer(URL_CENSOR_FILE, LAST_SIZE);
        Assert.assertFalse(isNew);
    }
}