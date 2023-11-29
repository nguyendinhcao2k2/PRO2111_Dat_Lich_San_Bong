package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author caodinh
 */
public class NuocUongExportExcel {
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static XSSFSheet sheet;

    // create header Line
    private static void writeHeader() {

        sheet = workbook.createSheet("Nước Uống");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Đơn Giá", cellStyle);
        createCell(row, 2, "Số Lượng", cellStyle);
        createCell(row, 3, "Tên Nước Uống", cellStyle);
    }

    private static void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
        // TODO Auto-generated method stub
        sheet.autoSizeColumn(countColumn);
        Cell cell = row.createCell(countColumn);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    public static void exportData(HttpServletResponse response) throws IOException {
        // calling method headerLine
        writeHeader();
        // calling methods writedataline
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();

    }
}
