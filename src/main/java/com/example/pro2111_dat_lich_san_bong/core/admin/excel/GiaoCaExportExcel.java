package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GiaoCaExportExcel {
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");

    public static void wirteTitle(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("GiaoCa");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        createCell(row, 0, "Nhân Viên Trong Ca", style);
        createCell(row, 1, "Nhân Viên Ca Tiếp Theo", style);
        createCell(row, 2, "Thời Gian Nhận Ca", style);
        createCell(row, 3, "Thời Gian Kết Ca", style);
        createCell(row, 4, "Thời Gian Reset", style);
        createCell(row, 5, "Tiền Ban Đầu", style);
        createCell(row, 6, "Tiền Phát Sinh", style);
        createCell(row, 7, "Tổng Tiền Mặt", style);
        createCell(row, 8, "Tổng Tiền Chuyển Khoản", style);
        createCell(row, 9, "Tiền Đã Rút", style);
        createCell(row, 10, "Tổng Tiền", style);
        createCell(row, 11, "Ghi Chú", style);
    }

    private static void createCell(Row row, int columnCount, Object value, CellStyle style) {
        row.getSheet().autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        //check xem value có phai la mot doi tuong cua lop ..Double không
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Timestamp) {
            cell.setCellValue((Timestamp) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private static String currenlyNumber(Double value) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }

    public static void writeData(XSSFWorkbook workbook, List<QuanLyGiaoCaResponse> listData) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (QuanLyGiaoCaResponse items : listData) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, items.getDisplayNameNhanVienTrongCa(), style);
            createCell(row, columnCount++, items.getDisplayNameNhanVienCaTiepTheo(), style);
            createCell(row, columnCount++, format.format(items.getThoiGianNhanCa()), style);
            createCell(row, columnCount++, format.format(items.getThoiGianKetCa()), style);
            createCell(row, columnCount++, items.getThoiGianReset() == null ? "" : format.format(items.getThoiGianReset()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTienBanDau()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTienPhatSinh()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTongTienMat()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTongTienKhac()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTongTienMatRut()), style);
            createCell(row, columnCount++, currenlyNumber(items.getTongTienTrongCa()), style);
            createCell(row, columnCount++, items.getGhiChuPhatSinh(), style);
        }
    }

    public static void exportData(HttpServletResponse response, List<QuanLyGiaoCaResponse> listData) throws IOException {
        // calling method headerLine
        XSSFWorkbook workbook = new XSSFWorkbook();
        wirteTitle(workbook);
        writeData(workbook, listData);
        // calling methods writedataline
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();
    }

}
