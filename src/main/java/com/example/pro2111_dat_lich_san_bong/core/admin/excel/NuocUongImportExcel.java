package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author caodinh
 */
public class NuocUongImportExcel {

    public static final int COLUMN_DON_GIA = 1;
    public static final int COLUMN_SO_LUONG = 2;
    public static final int COLUMN_TEN_NUOC_UONG = 3;

    public static List<NuocUong> readExcel(MultipartFile file) throws IOException {
        List<NuocUong> listBooks = new ArrayList<>();
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
            NuocUong nuocUong = new NuocUong();
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
                        if (donGiaStr.isEmpty() || donGiaStr.isBlank() || !matcherDonGia.matches()) {
                            throw new RestApiException("Đơn giá bị phải là số và khộng được blank");
                        } else {
                            double parsedDonGia = Double.parseDouble(donGiaStr);
                            nuocUong.setDonGia(parsedDonGia);
                        }
                        break;
                    case COLUMN_SO_LUONG:
                        String soLuongStr = String.valueOf(cellValue).trim();
                        Matcher matcherSoLuong = numberPattern.matcher(soLuongStr);
                        if (soLuongStr.isEmpty() || soLuongStr.isBlank() || !matcherSoLuong.matches()) {
                            throw new RestApiException("Số lượng  phải là số và khộng được blank");
                        } else {
                            double parsedSoLuong = Double.parseDouble(soLuongStr);
                            nuocUong.setSoLuong((int) parsedSoLuong);
                        }
                        break;
                    case COLUMN_TEN_NUOC_UONG:
                        String tenNuocUong = String.valueOf(cellValue).trim();
                        if (!tenNuocUong.isEmpty() || !tenNuocUong.isBlank()) {
                            nuocUong.setTenNuocUong(tenNuocUong);
                        } else {
                            throw new RestApiException("Tên nước khộng được blank");
                        }
                        break;
                    default:
                        break;
                }
            }
            nuocUong.setTrangThai(0);
            listBooks.add(nuocUong);
        }

        workbook.close();
        inputStream.close();

        return listBooks;
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
