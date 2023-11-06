package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
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
import java.util.List;

public class GiaoCaExportExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<QuanLyGiaoCaResponse> listData;

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public GiaoCaExportExcel(List<QuanLyGiaoCaResponse> listData) {
        this.listData = listData;
        workbook = new XSSFWorkbook();
    }

    public void wirteTitle() {
        sheet = workbook.createSheet("GiaoCa");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nhân Viên", style);
        createCell(row, 2, "Thoi_Gian_Nhan_Ca", style);
        createCell(row, 3, "Thoi_Gian_Ket_Ca", style);
        createCell(row, 4, "Thoi_Gian_Reset", style);
        createCell(row, 5, "Tien_Ban_Dau", style);
        createCell(row, 6, "Tien_Phat_Sinh", style);
        createCell(row, 7, "Tong_Tien_Mat", style);
        createCell(row, 8, "Tong_Chuyen_Khoan", style);
        createCell(row, 9, "Tien_Da_Rut", style);
        createCell(row, 10, "Tong_Tien", style);
        createCell(row, 11, "Ghi_Chu", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
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

    public void writeData() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (QuanLyGiaoCaResponse items : listData) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, items.getId(), style);
            createCell(row, columnCount++, items.getDisplayNameNhanVienTrongCa(), style);
            createCell(row, columnCount++, items.getThoiGianNhanCa(), style);
            createCell(row, columnCount++, items.getThoiGianKetCa(), style);
            createCell(row, columnCount++, items.getThoiGianReset(), style);
            createCell(row, columnCount++, items.getTienBanDau(), style);
            createCell(row, columnCount++, items.getTienPhatSinh(), style);
            createCell(row, columnCount++, items.getTongTienMat(), style);
            createCell(row, columnCount++, items.getTongTienKhac(), style);
            createCell(row, columnCount++, items.getTongTienMatRut(), style);
            createCell(row, columnCount++, items.getTongTienTrongCa(), style);
            createCell(row, columnCount++, items.getGhiChuPhatSinh(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        wirteTitle();
        writeData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(response.getOutputStream());
        workbook.close();
        outputStream.close();
    }
}
