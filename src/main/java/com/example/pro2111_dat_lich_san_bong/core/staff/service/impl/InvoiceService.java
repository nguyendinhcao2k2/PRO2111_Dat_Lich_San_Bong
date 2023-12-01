package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

    public byte[] generatePdf(HoaDonThanhToanRequest id) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            pdf.setDefaultPageSize(PageSize.A4);


            Paragraph headerTitle = new Paragraph("HOA DON THANH TOAN")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(headerTitle);

            Paragraph name = new Paragraph("SAN BONG DONG DE")
                    .setItalic()
                    .setFontSize(16)
                    .setBold()
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(name);

            Paragraph storeAddress = new Paragraph("SD-107 / FPT POLYTECHNIC")
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(storeAddress);

            document.add(createHeader("Thong Tin Khach Hang"));
            document.add(new Paragraph("\n"));
            document.add(createFieldAndShiftInfo("SAN THI DAU: ", String.valueOf(id.getTenSanBong())));
            String ngayBatDau = String.valueOf(id.getThoiGianBatDau());
            String ngayKetThuc = String.valueOf(id.getThoiGianKetThuc());
            document.add(createFieldAndShiftInfo("Ca: ",ngayBatDau+" - "+ngayKetThuc));
            document.add(new Paragraph("\n"));
            document.add(createCustomerInfo(String.valueOf(id.getTenKhachHang()), "San Bong Dong De", "Emai", String.valueOf(id.getSoDienThoai()), "Cityville"));


            document.add(new Paragraph("\n"));
            document.add(createHeader("Dich Vu"));
            document.add(new Paragraph("\n"));
            document.add(createServiceUsageTable(id));
            document.add(new Paragraph("\n"));
            document.add(createClosingMessage("Cam On Ban Da Su Dung San Bong!"));
            document.add(createClosingMessage("Lien He: 0356169620"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private static Table createHeader(String name) {
        Table header = new Table(UnitValue.createPercentArray(new float[]{1}));
        header.setWidth(UnitValue.createPercentValue(100));

        Cell cell = new Cell()
                .add(new Paragraph(name)
                        .setFontColor(new DeviceRgb(Color.BLACK))
                        .setBold()
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER))
                        .setMarginBottom(25)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(Color.GRAY));
        header.addCell(cell);
        return header;
    }

    private static Table createCustomerInfo(String name, String address, String email, String phone, String city) {
        Table customerInfo = new Table(UnitValue.createPercentArray(new float[]{2, 2}));
        customerInfo.setWidth(UnitValue.createPercentValue(100));

        customerInfo.addCell(createCell("Ten Khach Hang", name));
        customerInfo.addCell(createCell("Dia Chi", address));
        customerInfo.addCell(createCell("Email", email));
        customerInfo.addCell(createCell("So Dien Thoai", phone));
        customerInfo.addCell(createCell("City", city));

        // Add more rows as needed

        return customerInfo;
    }


    private static Table createServiceUsageTable(HoaDonThanhToanRequest id) {
        Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}));
        serviceTable.setWidth(UnitValue.createPercentValue(100));

        // Table Header
        serviceTable.addHeaderCell(createHeaderCell("Service Description"));
        serviceTable.addHeaderCell(createHeaderCell("Quantity"));
        serviceTable.addHeaderCell(createHeaderCell("Unit Price"));
        serviceTable.addHeaderCell(createHeaderCell("Total"));

        // Sample Service Data
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));

        serviceTable.addCell(createTotalCell("Tong Tien", String.valueOf(id.getTongTienSanCa())));

        return serviceTable;
    }

    private static Cell createCell(String header, String content) {
        return new Cell().add(new Paragraph(header))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph(content).setBold());
    }

    private static Cell createCellData( String data) {
        return new Cell().add(new Paragraph(data))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text))
                .setBackgroundColor(new DeviceGray(0.75f))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell createTotalCell(String header, String total) {
        return new Cell(1, 4).add(new Paragraph(header).setBold())
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Paragraph(total));
    }

    private static Paragraph createFieldAndShiftInfo(String fieldInfo, String title) {
        Paragraph info = new Paragraph()
                .add(new Paragraph(fieldInfo).setFontSize(12).setBold())
                .add(new Paragraph(title).setFontSize(12).setBold()).setFontColor(new DeviceRgb(Color.RED));
        return info;
    }


    private static Paragraph createClosingMessage(String text) {
        return new Paragraph(text)
                .setFontSize(12)
                .setItalic()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20);
    }
}



