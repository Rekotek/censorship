package com.scriptorium.censorship.parser.util;

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

public class BookXlsMapper {
    private static final Logger log = LoggerFactory.getLogger(BookXlsMapper.class);

    private static final Map<Integer, Method> columnMap = new HashMap<>();

    static {
        try {
            columnMap.put(5, BookInfoBuilder.class.getMethod("tradeCompany", String.class));
            columnMap.put(15, BookInfoBuilder.class.getMethod("ruTitle", String.class));
            columnMap.put(16, BookInfoBuilder.class.getMethod("isbn", String.class));
            columnMap.put(17, BookInfoBuilder.class.getMethod("quantity", String.class));
            columnMap.put(19, BookInfoBuilder.class.getMethod("author", String.class));
            columnMap.put(21, BookInfoBuilder.class.getMethod("uaTitle", String.class));
            columnMap.put(22, BookInfoBuilder.class.getMethod("publisher", String.class));
            columnMap.put(23, BookInfoBuilder.class.getMethod("yearOfPublish", String.class));
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
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        });
        return bookInfoRowBuilder.build();
    }

    public static List<BookInfo> parseXlsStream(InputStream stream, int rowsToOmit) throws IOException {
        List<BookInfo> resultList = new ArrayList<>(1024);
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
                resultList.add(bookInfo);
            }
        }
        log.info("XLS stream was successfully parsed. Book quantity: {}", resultList.size());
        return resultList;
    }
}
