package com.scriptorium.censorship.parser.util;

import com.scriptorium.censorship.common.model.BookDto;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class BookDtoXlsMapperTest {
    @Test
    @Ignore
    public void readFromXlsFile() throws IOException {
        URL stream = BookDtoXlsMapperTest.class.getClassLoader().getResource("ExampleGoods.xls");
        File file = new File(stream.getFile());
        List<BookDto> bookDtoList = BookXlsMapper.parseXlsFile(file, 2, 30000);
        bookDtoList.forEach(System.out::println);
    }

    @Test
    public void readFromXlsxFile() throws IOException {
        URL stream = BookDtoXlsMapperTest.class.getClassLoader().getResource("ExampleGoods.xlsx");
        File file = new File(stream.getFile());
        List<BookDto> bookDtoList = BookXlsMapper.parseXlsFile(file, 2, 30000);
        bookDtoList.forEach(System.out::println);
    }
}