package com.scriptorium.censorship.parser.util;

import com.scriptorium.censorship.common.model.BookDto;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class BookDtoXlsMapperTest {
    private static final String URL_CENSOR_FILE = "http://comin.kmu.gov.ua/document/134477/Goods.xls";

    private void loadUrl() {
        String urlCensorFile;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("parser.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            urlCensorFile = prop.getProperty("site.url", URL_CENSOR_FILE);
            System.out.println(urlCensorFile);
        } catch (IOException e) {
            urlCensorFile = URL_CENSOR_FILE;
        }
    }
    @Test
    public void readFromFile() throws IOException {
        try (InputStream stream = BookDtoXlsMapperTest.class.getResourceAsStream("/ExampleGoods.xls")) {
            List<BookDto> bookDtoList = BookXlsMapper.parseXlsStream(stream, 2);
            bookDtoList.forEach(System.out::println);
        }
    }

}