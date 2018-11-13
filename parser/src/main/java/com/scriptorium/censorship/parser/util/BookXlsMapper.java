package com.scriptorium.censorship.parser.util;

import com.scriptorium.censorship.common.model.BookDto;
import com.scriptorium.censorship.parser.model.BookInfo;
import com.scriptorium.censorship.parser.model.BookInfo.BookInfoBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.scriptorium.censorship.common.util.Converters.convertStringValue;

public class BookXlsMapper {
    private static final Logger log = LoggerFactory.getLogger(BookXlsMapper.class);

    private static final Map<Integer, Method> columnMap = new HashMap<>();

    private static final int TRADE_COMPANY_COLUMN = 5;
    private static final int RU_TITLE_COLUMN = 15;
    private static final int ISBN_COLUMN = 16;
    private static final int QUANTITY_COLUMN = 17;
    private static final int AUTHOR_COLUMN = 19;
    private static final int UA_TITLE_COLUMN = 21;
    private static final int PUBLISHER_COLUMN = 22;
    private static final int YEAR_COLUMN = 23;

    static {
        try {
            columnMap.put(TRADE_COMPANY_COLUMN, BookInfoBuilder.class.getMethod("tradeCompany", String.class));
            columnMap.put(RU_TITLE_COLUMN, BookInfoBuilder.class.getMethod("ruTitle", String.class));
            columnMap.put(ISBN_COLUMN, BookInfoBuilder.class.getMethod("isbn", String.class));
            columnMap.put(QUANTITY_COLUMN, BookInfoBuilder.class.getMethod("quantity", String.class));
            columnMap.put(AUTHOR_COLUMN, BookInfoBuilder.class.getMethod("author", String.class));
            columnMap.put(UA_TITLE_COLUMN, BookInfoBuilder.class.getMethod("uaTitle", String.class));
            columnMap.put(PUBLISHER_COLUMN, BookInfoBuilder.class.getMethod("publisher", String.class));
            columnMap.put(YEAR_COLUMN, BookInfoBuilder.class.getMethod("yearOfPublish", String.class));
        } catch (NoSuchMethodException e) {
            log.error("Initialization error: {}", e.getMessage());
        }
    }

    private BookXlsMapper() {
    }

    private static BookInfo createFromRow(Row row) throws IllegalArgumentException {
        BookInfoBuilder bookInfoRowBuilder = BookInfo.builder();
        columnMap.forEach((col, method) -> {
            Cell cell = row.getCell(col);
            CellType cellType = cell.getCellType();
            try {
                if (cellType == CellType.STRING) {
                    method.invoke(bookInfoRowBuilder, cell.getStringCellValue());
                } else if (cellType == CellType.NUMERIC) {
                    try {
                        int numericCellValue = (int) cell.getNumericCellValue();
                        method.invoke(bookInfoRowBuilder, String.valueOf(numericCellValue));
                    } catch (IllegalArgumentException ignored) {
                        log.error("Illegal Argument Exception. Row = {}; Col = {}; Cell Value = {}", row.getRowNum(), col, cell);
                        method.invoke(bookInfoRowBuilder, "");
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException ignored) { }
        });
        return bookInfoRowBuilder.build();
    }

    public static List<BookDto> parseXlsStream(InputStream stream, int rowsToOmit) throws IOException {
        List<BookDto> resultList = new ArrayList<>(1024);
        try (HSSFWorkbook wb = new HSSFWorkbook(stream)) {
            log.debug("Begin to parse Workbook: Number of sheets: {}; Active sheet index: {}.", wb.getNumberOfSheets(), wb.getActiveSheetIndex());
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
            while (it.hasNext()) {
                Row row = it.next();
                if (row.getRowNum() == rowsToOmit) {
                    break;
                }
            }
            while (it.hasNext()) {
                Row row = it.next();
                BookInfo bookInfo = BookXlsMapper.createFromRow(row);
                resultList.add(createBookDto(bookInfo));
            }
        }
        log.info("XLS stream was successfully parsed. Books quantity: {}", resultList.size());
        return resultList;
    }

    private static BookDto createBookDto(BookInfo bookInfo) {
        return BookDto.builder()
                .tradeCompany(bookInfo.getTradeCompany())
                .publisher(bookInfo.getPublisher())
                .ruTitle(bookInfo.getRuTitle())
                .isbn(bookInfo.getIsbn())
                .quantity(convertStringValue(bookInfo.getQuantity()))
                .author(bookInfo.getAuthor())
                .uaTitle(bookInfo.getUaTitle())
                .yearOfPublish(convertStringValue(bookInfo.getYearOfPublish()))
                .build();
    }
}
