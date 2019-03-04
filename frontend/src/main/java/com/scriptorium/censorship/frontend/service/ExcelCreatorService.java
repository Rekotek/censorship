package com.scriptorium.censorship.frontend.service;

import com.scriptorium.censorship.frontend.entity.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ExcelCreatorService {
    private static final Logger LOG = getLogger(ExcelCreatorService.class);

    private final BookService bookService;
    private final static String[] columns = {"Автор", "Название", "ISBN", "Год", "№ разр.", "Дата разрешения", "Количество"};

    public ExcelCreatorService(BookService bookService) {
        this.bookService = bookService;
    }


    public ByteArrayOutputStream buildWorkbook(List<Book> bookList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчёт");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.DARK_BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle centeredCellStyle = workbook.createCellStyle();
        centeredCellStyle.setAlignment(HorizontalAlignment.CENTER);

        //  https://stackoverflow.com/questions/5794659/poi-how-do-i-set-cell-value-to-date-and-apply-default-excel-date-format
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd MMM yyyy"));

        CellStyle yearCellStyle = workbook.createCellStyle();
        DataFormat yearFormat = workbook.createDataFormat();
        yearCellStyle.setDataFormat(yearFormat.getFormat("####"));
        yearCellStyle.setAlignment(HorizontalAlignment.CENTER);

        int rowNum = 1;
        Cell cell;
        for (Book book: bookList) {
            Row row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(book.getAuthor());
            cell.setCellType(CellType.STRING);

            cell = row.createCell(1);
            cell.setCellValue(book.getRuTitle());
            cell.setCellType(CellType.STRING);

            cell = row.createCell(2);
            cell.setCellValue(book.getIsbn());
            cell.setCellType(CellType.STRING);
            cell.setCellStyle(centeredCellStyle);

            cell = row.createCell(3);
            cell.setCellValue(book.getYearOfPublish());
            cell.setCellType(CellType.NUMERIC);
            cell.setCellStyle(yearCellStyle);

            cell = row.createCell(4);
            cell.setCellValue(book.getDocumentNum());
            cell.setCellStyle(centeredCellStyle);
            cell.setCellType(CellType.STRING);

            cell = row.createCell(5);
            cell.setCellValue(book.getDocumentDate());
            cell.setCellStyle(dateCellStyle);

            cell = row.createCell(6);
            cell.setCellValue(book.getQuantity());
            cell.setCellType(CellType.NUMERIC);
            cell.setCellStyle(centeredCellStyle);
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(bookList.size() * columns.length);
        try {
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return out;
    }
}
