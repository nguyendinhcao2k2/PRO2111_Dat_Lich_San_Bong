package com.example.pro2111_dat_lich_san_bong.core.admin.excel;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaImportExcel {
    public static final int COLUMN_GIA_CA = 4;
    public static final int COLUMN_THOI_GIAN_KET_THUC = 3;
    public static final int COLUMN_THOI_GIAN_BAT_DAU = 2;
    public static final int COLUMN_TEN_CA = 1;

    private static boolean kiemTraKieuThoiGian(String thoiGian) {
        try {
            System.out.println("Hello"+thoiGian);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(thoiGian, formatter);
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Time formatTime(String ngay){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(ngay,dateTimeFormatter);
        int h = localTime.getHour();
        int m = localTime.getMinute();
        int s = localTime.getSecond();
        return new Time(h,m,s);
    }

    public static List<Ca> readExcel(MultipartFile file) throws IOException, ParseException {
        List<Ca> caList = new ArrayList<>();
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
           Ca ca = new Ca();
            while (cellIterator.hasNext()) {
                // Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_GIA_CA:
                        String donGiaStr = String.valueOf(cellValue).trim();
                        Matcher matcherDonGia = numberPattern.matcher(donGiaStr);
                        if (donGiaStr.isEmpty() || donGiaStr.isBlank()) {
                            ca.setGiaCa(0.0);
                        } else if (!matcherDonGia.matches()) {
                            String regexNumber = donGiaStr.replaceAll("\\D", "");
                            if (regexNumber.isEmpty() || regexNumber.isBlank() || regexNumber == null || regexNumber.equals("")) {
                                ca.setGiaCa(0.0);
                            } else {
                                double parsedDonGia = Double.parseDouble(regexNumber);
                                ca.setGiaCa(parsedDonGia);
                            }
                        } else {
                            double parsedDonGia = Double.parseDouble(donGiaStr);
                            ca.setGiaCa(parsedDonGia);
                        }
                        break;
                    case COLUMN_THOI_GIAN_BAT_DAU:
                        String thoiGianBD = String.valueOf(cellValue).trim();
                        if (thoiGianBD.isEmpty() || thoiGianBD.isBlank() || !kiemTraKieuThoiGian(thoiGianBD)) {
                            throw new RestApiException("Dữ liệu không đúng định dạng hoặc đang để trống");
                        }else{
                            ca.setThoiGianBatDau(formatTime(thoiGianBD));
                        }
                        break;
                    case COLUMN_THOI_GIAN_KET_THUC:
                        String thoiGianKT = String.valueOf(cellValue).trim();
                        if (thoiGianKT.isEmpty() || thoiGianKT.isBlank() || !kiemTraKieuThoiGian(thoiGianKT)) {
                            throw new RestApiException("Dữ liệu không đúng định dạng hoặc đang để trống");
                        }else{
                            ca.setThoiGianKetThuc(formatTime(thoiGianKT));
                        }
                        break;
                    case COLUMN_TEN_CA:
                        String tenCa = String.valueOf(cellValue).trim();
                        if (!tenCa.isEmpty() || !tenCa.isBlank()) {
                            ca.setTenCa(tenCa);
                        } else {
                            throw new RestApiException("Tên nước khộng được blank");
                        }
                        break;

                    default:
                        break;
                }
            }
            ca.setTrangThai(0);
            caList.add(ca);
        }

        workbook.close();
        inputStream.close();

        return caList;
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
