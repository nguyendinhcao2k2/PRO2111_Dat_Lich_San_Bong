package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DoThueImportExcel {

    public static final int COLUMN_DON_GIA = 3;
    public static final int COLUMN_IMAGE = 2;
    public static final int COLUMN_SO_LUONG = 4;
    public static final int COLUMN_TEN_DO_THUE = 1;

    public static List<DoThue> readExcel(MultipartFile file) throws IOException {
        List<DoThue> listDoThue = new ArrayList<>();
        Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        // Get InputStream from MultipartFile
        InputStream inputStream = file.getInputStream();

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, file.getOriginalFilename());

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            DoThue doThue = new DoThue();
            while (cellIterator.hasNext()) {
                // Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_DON_GIA:
                        String donGiaStr = String.valueOf(cellValue).trim();
                        Matcher matcherDonGia = numberPattern.matcher(donGiaStr);
                        if (donGiaStr.isEmpty() || donGiaStr.isBlank()) {
                            doThue.setDonGia(0.0);
                        } else if (!matcherDonGia.matches()) {
                            String regexNumber = donGiaStr.replaceAll("\\D", "");
                            if (regexNumber.isEmpty() || regexNumber.isBlank() || regexNumber == null || regexNumber.equals("")) {
                                doThue.setDonGia(0.0);
                            } else {
                                double parsedDonGia = Double.parseDouble(regexNumber);
                                doThue.setDonGia(parsedDonGia);
                            }
                        } else {
                            double parsedDonGia = Double.parseDouble(donGiaStr);
                            doThue.setDonGia(parsedDonGia);
                        }
                        break;
                    case COLUMN_SO_LUONG:
                        String soLuongStr = String.valueOf(cellValue).trim();
                        Matcher matcherSoLuong = numberPattern.matcher(soLuongStr);
                        if (soLuongStr.isEmpty() || soLuongStr.isBlank()) {
                            doThue.setSoLuong(0);
                        } else if (!matcherSoLuong.matches()) {
                            String regex = soLuongStr.replaceAll("\\D", "");
                            if (regex.isEmpty() || regex.isBlank() || regex == null || regex.equals("")) {
                                doThue.setSoLuong(0);
                            } else {
                                double parsedSoLuong = Double.parseDouble(regex);
                                doThue.setSoLuong((int) parsedSoLuong);
                            }
                        } else {
                            double parsedSoLuong = Double.parseDouble(soLuongStr);
                            doThue.setSoLuong((int) parsedSoLuong);
                        }
                        break;
                    case COLUMN_TEN_DO_THUE:
                        String tenDoThue = String.valueOf(cellValue).trim();
                        if (!tenDoThue.isEmpty() || !tenDoThue.isBlank()) {
                            doThue.setTenDoThue(tenDoThue);
                        } else {
                            throw new RestApiException("Tên nước khộng được blank");
                        }
                        break;
                    case COLUMN_IMAGE:
                        byte[] imageBytes = getImageBytesFromRow(nextRow, columnIndex);
                        if (imageBytes != null) {
                            String imgBase64 = Base64.getEncoder().encodeToString(imageBytes);
                            doThue.setImage(imgBase64);
                        } else {
                            // Ảnh trống, có thể bỏ qua hoặc xử lý theo ý muốn của bạn
                            doThue.setImage(null); // hoặc thực hiện xử lý khác nếu cần
                        }
                        break;
                    default:
                        break;
                }
            }
            doThue.setTrangThai(0);
            listDoThue.add(doThue);
        }

        workbook.close();
        inputStream.close();

        return listDoThue;
    }

    private static byte[] getImageBytesFromRow(Row row, int columnIndex) {
        try {
            XSSFDrawing drawing = (XSSFDrawing) row.getSheet().createDrawingPatriarch();

            for (Cell cell : row) {
                if (cell instanceof XSSFCell && cell.getColumnIndex() == columnIndex) {
                    XSSFCell xssfCell = (XSSFCell) cell;
                    for (XSSFShape shape : drawing.getShapes()) {
                        if (shape instanceof XSSFPicture) {
                            XSSFPicture picture = (XSSFPicture) shape;
                            if (picture.getClientAnchor().getRow1() == row.getRowNum() && picture.getClientAnchor().getCol1() == cell.getColumnIndex()) {
                                // Truy cập trực tiếp dữ liệu ảnh từ XSSFPicture
                                return picture.getPictureData().getData();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý exception nếu cần
        }
        return null;
    }


    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Vui lòng chọn file excel");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}
