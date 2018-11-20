package com.scriptorium.censorship.parser.util;

import com.scriptorium.censorship.common.model.BookDto;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BookDtoXlsMapperTest {
    @Test
    public void readFromXlsFile() throws IOException {
        try (InputStream stream = BookDtoXlsMapperTest.class.getResourceAsStream("/ExampleGoods.xls")) {
            List<BookDto> bookDtoList = BookXlsMapper.parseXlsStream(stream, 2);
            bookDtoList.forEach(System.out::println);
        }
    }

    @Test
    public void readFromXlsxFile() throws IOException {
        try (InputStream stream = BookDtoXlsMapperTest.class.getResourceAsStream("/ExampleGoods.xlsx")) {
            List<BookDto> bookDtoList = BookXlsMapper.parseXlsStream(stream, 2);
            bookDtoList.forEach(System.out::println);
        }
    }

}