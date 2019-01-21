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

    @Test
    public void loadFileFromUrl() {
        List<BookDto> bookDtoList = BookListLoader.loadDataFromUrl(URL_CENSOR_FILE, false);
        Assert.assertTrue(bookDtoList.size() > 0);
        System.out.println("==============First 3 books=============");
        System.out.println(bookDtoList.get(0));
        System.out.println(bookDtoList.get(1));
        System.out.println(bookDtoList.get(2));
        System.out.println("==============Last 3 books=============");
        int lastIndex = bookDtoList.size() - 5000;
        System.out.println(bookDtoList.get(lastIndex));
        System.out.println(bookDtoList.get(lastIndex - 1));
        System.out.println(bookDtoList.get(lastIndex - 2));
    }

    @Test
    public void getFileSize() {
        long fileSize = BookListLoader.loadContentParams(URL_CENSOR_FILE).getFileSize();
        Assert.assertTrue(fileSize > 0);
        System.out.println(fileSize);
    }
}