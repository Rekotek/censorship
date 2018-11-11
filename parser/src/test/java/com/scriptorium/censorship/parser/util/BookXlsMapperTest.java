package com.scriptorium.censorship.parser.util;

import com.scriptorium.censorship.parser.model.BookInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class BookXlsMapperTest {

    @Test
    public void readFromFile() throws IOException {
        try (InputStream stream = BookXlsMapperTest.class.getResourceAsStream("/ExampleGoods.xls")) {
            List<BookInfo> bookInfoList = BookXlsMapper.parseXlsStream(stream, 2);
            bookInfoList.forEach(System.out::println);
        }
    }

    @Test
    public void readFromUrl() throws IOException {
        URL urlObject = new URL("http://comin.kmu.gov.ua/document/134477/Goods.xls");
        URLConnection urlConnection = urlObject.openConnection();
        try (InputStream stream = urlConnection.getInputStream()) {
            List<BookInfo> bookInfoList = BookXlsMapper.parseXlsStream(stream, 2);

            System.out.println("==============First 3 books=============");
            System.out.println(bookInfoList.get(0));
            System.out.println(bookInfoList.get(1));
            System.out.println(bookInfoList.get(2));
            System.out.println("==============Last 3 books=============");
            int lastIndex = bookInfoList.size() - 1;
            System.out.println(bookInfoList.get(lastIndex));
            System.out.println(bookInfoList.get(lastIndex - 1));
            System.out.println(bookInfoList.get(lastIndex - 2));

            //bookInfoList.forEach(book -> System.out.println(book));
        }

    }

}