package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CaExportExcel {

    private static void writeHeader(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Ca Sân Bóng");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên Ca", cellStyle);
        createCell(row, 2, "Thời gian bắt đầu", cellStyle);  // Đổi tên cột từ "Đơn Giá" thành "Ảnh"
        createCell(row, 3, "Thời gian kết thúc", cellStyle);
        createCell(row, 4, "Giá ca", cellStyle);
    }

    private static void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
        row.getSheet().autoSizeColumn(countColumn);
        Cell cell = row.createCell(countColumn);
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Time) {
            cell.setCellValue((Time) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    public static void writeData(XSSFWorkbook workbook, List<Ca> caList) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;

        // Tạo một kiểu dữ liệu định dạng văn bản
        CellStyle textCellStyle = workbook.createCellStyle();
        DataFormat textDataFormat = workbook.createDataFormat();
        textCellStyle.setDataFormat(textDataFormat.getFormat("@"));

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        int index = 0;

        for (Ca items : caList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, items.getTenCa(), style);

            // Thay đổi định dạng ô thành văn bản cho thời gian bắt đầu
            Cell cellStartTime = row.createCell(columnCount++);
            cellStartTime.setCellStyle(textCellStyle); // Sử dụng kiểu dữ liệu văn bản
            cellStartTime.setCellValue(format.format(items.getThoiGianBatDau()));

            // Thay đổi định dạng ô thành văn bản cho thời gian kết thúc
            Cell cellEndTime = row.createCell(columnCount++);
            cellEndTime.setCellStyle(textCellStyle); // Sử dụng kiểu dữ liệu văn bản
            cellEndTime.setCellValue(format.format(items.getThoiGianKetThuc()));

            // Định dạng cột giá cả
            createCell(row, columnCount++, currencyFormat.format(items.getGiaCa()), style);
        }
    }



    public static void exportData(HttpServletResponse response, List<Ca> caList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeader(workbook);
        writeData(workbook, caList);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();
    }
}
