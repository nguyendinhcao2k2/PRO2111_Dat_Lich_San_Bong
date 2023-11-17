package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.DoThueResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.QuanLyGiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.DoThueService;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caodinh
 */
public class DoThueExportExcel {
    // create header Line
    private static void writeHeader(XSSFWorkbook workbook) {

        XSSFSheet sheet  = workbook.createSheet("DoThue");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên Đồ Thuê", cellStyle);
        createCell(row, 2, "Ảnh", cellStyle);
        createCell(row, 3, "Đơn Giá", cellStyle);
        createCell(row, 4, "Số Lượng", cellStyle);

    }

    private static void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
        // TODO Auto-generated method stub
        row.getSheet().autoSizeColumn(countColumn);
        Cell cell = row.createCell(countColumn);
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    //    thêm
    public static void writeData(XSSFWorkbook workbook,List<DoThue> doThueList) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int index = 0;
        for (DoThue items : doThueList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, items.getTenDoThue(), style);
            createCell(row, columnCount++, items.getImage(), style);
            createCell(row, columnCount++, items.getDonGia(), style);
            createCell(row, columnCount++, items.getSoLuong(), style);


        }
    }
//    them

    public static void exportData(HttpServletResponse response, List<DoThue> doThueList) throws IOException {
        // calling method headerLine
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeader(workbook);
        writeData(workbook,doThueList);
        // calling methods writedataline
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();

    }
}
