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
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static XSSFSheet sheet;

    @Autowired
    private static DoThueService doThueService;

    // create header Line
    private static void writeHeader() {

        sheet = workbook.createSheet("Đồ Thuê");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Đơn Giá", cellStyle);
        createCell(row, 2, "Số Lượng", cellStyle);
        createCell(row, 3, "Tên Đồ Thuê", cellStyle);
    }

    private static void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
        // TODO Auto-generated method stub
        sheet.autoSizeColumn(countColumn);
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
    public static void writeData() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        List<DoThue> doThueList = new ArrayList<DoThue>();
        doThueList.add(new DoThue("564654654", "ád", "5345", 6546, 5345.5, 0));
        doThueList.add(new DoThue("564654654", "ád", "5345", 6546, 5345.5, 0));
        doThueList.add(new DoThue("564654654", "ád", "5345", 6546, 5345.5, 0));
        doThueList.add(new DoThue("564654654", "ád", "5345", 6546, 5345.5, 0));
        int index = 0;
        for (DoThue items : doThueList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, items.getDonGia(), style);
            createCell(row, columnCount++, items.getSoLuong(), style);
            createCell(row, columnCount++, items.getTenDoThue(), style);

        }
    }
//    them

    public static void exportData(HttpServletResponse response) throws IOException {
        // calling method headerLine
        writeHeader();
        writeData();
        // calling methods writedataline
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();

    }
}
