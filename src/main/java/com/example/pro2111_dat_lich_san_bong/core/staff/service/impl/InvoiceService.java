package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;

    @Autowired
    private HoaDonStaffRepository hoaDonStaffRepository;
    @Autowired
    private static IDichVuSanBongStaffService iDichVuSanBongStaffService;

    public byte[] generatePdf(HoaDonThanhToanRequest hoaDonThanhToanRequest) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNHaiBaTrung.TTF", "Identity-H", true);
        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4);
            Paragraph headerTitle = new Paragraph("HÓA ĐƠN THANH TOÁN")
                    .setFont(font)
                    .setFontSize(40)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(headerTitle);

            Paragraph name = new Paragraph("SÂN BÓNG ĐỒNG ĐẾ")
                    .setItalic()
                    .setFont(font)
                    .setFontSize(30)
                    .setBold()
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(name);

            Paragraph storeAddress = new Paragraph("SD-107 / FPT POLYTECHNIC")
                    .setFontSize(19)
                    .setFont(font)
                    .setItalic()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(storeAddress);

            document.add(createHeader("THÔNG TIN KHÁCH HÀNG")
                    .setFont(font)
                    .setFontSize(40));
            document.add(createFieldAndShiftInfo("Sân Thi Đấu: ", String.valueOf(hoaDonThanhToanRequest.getTenSanBong() + " - " + hoaDonThanhToanRequest.getTenLoaiSan() + hoaDonThanhToanRequest.getTenCa()))
                    .setFont(font)
                    .setFontSize(25));
            String thoiGianBatDau = String.valueOf(hoaDonThanhToanRequest.getThoiGianBatDau());
            String thoiGianKetThuc = String.valueOf(hoaDonThanhToanRequest.getThoiGianKetThuc());
            document.add(createFieldAndShiftInfo("Thời Gian: ", thoiGianBatDau + " - " + thoiGianKetThuc)
                    .setFont(font)
                    .setFontSize(25));
            document.add(createCustomerInfo(String.valueOf(hoaDonThanhToanRequest.getTenKhachHang()), "San Bong Dong De", "Emai", String.valueOf(hoaDonThanhToanRequest.getSoDienThoai())));


            document.add(new Paragraph("\n"));
            document.add(createHeader("DỊCH VU SỬ DỤNG").setFont(font)
                    .setFontSize(40));
            document.add(new Paragraph("\n"));
            document.add(createServiceUsageTable(hoaDonThanhToanRequest));
            document.add(createClosingMessage("CẢM ƠN VÌ ĐÃ CHỌN CHÚNG TÔI! - - LIÊN HỆ: 0356169620")
                    .setFont(font)
                    .setFontSize(25));
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
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER))
                .setMarginBottom(25)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(Color.GRAY));
        header.addCell(cell);
        return header;
    }

    private static Table createCustomerInfo(String name, String address, String email, String phone) throws IOException {
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNHaiBaTrung.TTF", "Identity-H", true);
        Table customerInfo = new Table(UnitValue.createPercentArray(new float[]{2, 2}));
        customerInfo.setWidth(UnitValue.createPercentValue(100));

        customerInfo.addCell(createCell("TÊN KHÁCH HÀNG", name).setFont(font));
        customerInfo.addCell(createCell("ĐỊA CHỈ", address).setFont(font));
        customerInfo.addCell(createCell("EMAIL", email).setFont(font));
        customerInfo.addCell(createCell("SỐ ĐIỆN THOẠI", phone).setFont(font));
        // Add more rows as needed

        return customerInfo;
    }


    private static Table createServiceUsageTable(HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}));
        serviceTable.setWidth(UnitValue.createPercentValue(100));

        // Table Header
        serviceTable.addHeaderCell(createHeaderCell("TÊN DỊCH VỤ"));
        serviceTable.addHeaderCell(createHeaderCell("SỐ LƯỢNG"));
        serviceTable.addHeaderCell(createHeaderCell("GIÁ"));
        serviceTable.addHeaderCell(createHeaderCell("TỔNG"));

        // Sample Service Data
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createCellData("Service 1"));
        serviceTable.addCell(createTotalCell("Tong Tien", String.valueOf(hoaDonThanhToanRequest.getTongTienSanCa())));

        return serviceTable;
    }

    private static Cell createCell(String header, String content) {
        return new Cell().add(new Paragraph(header).setFontSize(20))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph(content).setFontSize(20));
    }

    private static Cell createCellData(String data) {
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
        return new Cell(1, 4).add(new Paragraph(header))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Paragraph(total));
    }

    private static Paragraph createFieldAndShiftInfo(String fieldInfo, String title) {
        Paragraph info = new Paragraph()
                .add(new Paragraph(fieldInfo).setFontSize(12))
                .add(new Paragraph(title).setFontSize(12)).setFontColor(new DeviceRgb(Color.BLACK));
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



