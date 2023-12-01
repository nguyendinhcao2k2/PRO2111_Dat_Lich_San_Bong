package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class NuocUongExportExcel {
    private static void writeHeader(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("DoThue");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "STT", cellStyle);
        createCell(row, 1, "Tên Nước", cellStyle);
        createCell(row, 2, "Ảnh", cellStyle);  // Đổi tên cột từ "Đơn Giá" thành "Ảnh"
        createCell(row, 3, "Đơn Giá", cellStyle);
        createCell(row, 4, "Số Lượng", cellStyle);
    }

    private static void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
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

    private static void createImageCell(Row row, int column, String base64Image, Workbook workbook, CellStyle style) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Tạo ảnh từ dữ liệu bytes
        int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = row.getSheet().createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(column);
        anchor.setRow1(row.getRowNum());

        // Tạo ảnh từ vị trí và kích thước được chọn
        Picture picture = drawing.createPicture(anchor, pictureIdx);

        // Thay đổi kích thước ảnh
        int desiredWidth = 1;  // Thay đổi kích thước mong muốn
        int desiredHeight = 1;
        picture.resize(desiredWidth, desiredHeight);

        Cell cell = row.createCell(column);
        cell.setCellValue(""); // Giữ ô trống vì ảnh đã được chèn vào ô
        cell.setCellStyle(style);
    }

    private static boolean checkBase64(String base64) {
        // Biểu thức chính quy để kiểm tra chuỗi có phải là base64 không
        String base64Pattern = "^(data:image/[^;]+;base64,)?[A-Za-z0-9+/]+={0,2}$";

        // Kiểm tra sự khớp với biểu thức chính quy
        return Pattern.matches(base64Pattern, base64);
    }

    public static void writeData(XSSFWorkbook workbook, List<NuocUong> nuocUongList) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int index = 0;
        for (NuocUong items : nuocUongList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, items.getTenNuocUong(), style);

            // Chèn hình ảnh vào ô
            if ( items.getImage() == null || items.getImage().equals("") || items.getImage().isEmpty() || items.getImage().isBlank()) {
                createCell(row, columnCount++, "Chưa cập nhật", style);
            } else {
                if (!checkBase64(items.getImage())) {
                    createCell(row, columnCount++, "Chưa cập nhật", style);
                } else {
                    createImageCell(row, columnCount++, items.getImage(), workbook, style);
                }
            }

            createCell(row, columnCount++, currencyFormat.format(items.getDonGia()), style);
            createCell(row, columnCount++, items.getSoLuong(), style);
        }
    }


    public static void exportData(HttpServletResponse response, List<NuocUong> nuocUongList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeader(workbook);
        writeData(workbook, nuocUongList);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        workbook.close();
        outputStream.close();
    }
}
