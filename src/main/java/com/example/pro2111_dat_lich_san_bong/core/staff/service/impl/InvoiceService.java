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
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNThoiNay_R.TTF", "Identity-H", true);
        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4);
            Paragraph headerTitle = new Paragraph("SÂN BÓNG ĐỒNG ĐẾ")
                    .setFont(font)
                    .setFontSize(25)
                    .setBold()
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(headerTitle);

            Paragraph name = new Paragraph("HÓA ĐƠN THANH TOÁN")
                    .setFont(font)
                    .setFontSize(28)
                    .setBold()
                    .setMarginBottom(10)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(name);


            document.add(
                    createFieldAndShiftInfo("Tên Nhân Viên: ", " "+"Nguyễn Phúc Lâm")
                            .setFont(font)
                            .setTextAlignment(TextAlignment.RIGHT)
            );

            document.add(
                    createFieldAndShiftInfo("Sân Thi Đấu: ",
                            String.valueOf(
                                    " "+
                                            hoaDonThanhToanRequest.getTenSanBong() + " - " +
                                            hoaDonThanhToanRequest.getTenLoaiSan() + " - " +
                                            hoaDonThanhToanRequest.getTenCa()
                            )
                    )
                            .setFont(font)
                            .setTextAlignment(TextAlignment.RIGHT)
            );

//
            String thoiGianBatDau = String.valueOf(hoaDonThanhToanRequest.getThoiGianBatDau());
            String thoiGianKetThuc = String.valueOf(hoaDonThanhToanRequest.getThoiGianKetThuc());
            document.add(createFieldAndShiftInfo("Thời Gian: "," "+ thoiGianBatDau + " - " + thoiGianKetThuc)
                    .setFont(font)
                    .setTextAlignment(TextAlignment.RIGHT)
            );


            document.add(
                    createHeader("Thông Tin Khách Hàng")
                    .setFont(font)
                    .setFontSize(20)
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
            );

//            add địa chỉ khách hàng vào address, email (hiện tại đang fix cứng)
            document.add(
                    createCustomerInfo(
                            String.valueOf(hoaDonThanhToanRequest.getTenKhachHang()),
                            "Sân Bóng Đồng Đế", "Emai",
                            String.valueOf(hoaDonThanhToanRequest.getSoDienThoai())
                    )
            );


            document.add(new Paragraph("\n"));

            document.add(createHeader("Dịch Vụ Sử Dụng").setFont(font)
                    .setFontSize(20)
            );


            document.add(createServiceUsageTable(hoaDonThanhToanRequest,font));

            document.add(createClosingMessage("Xin Cảm Ơn Quý Khách! -- Liên Hệ: 0356169620")
                    .setFont(font)
                    .setFontSize(15));
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
                .setMarginBottom(10)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(63, 169, 219));
        header.addCell(cell);
        return header;
    }

    private static Table createCustomerInfo(String name, String address, String email, String phone) throws IOException {
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNThoiNay_R.TTF", "Identity-H", true);
        Table customerInfo = new Table(UnitValue.createPercentArray(new float[]{2, 2}));
        customerInfo.setWidth(UnitValue.createPercentValue(100));

        customerInfo.addCell(createHeaderCellInFo("Tên Khách Hàng").setFont(font));
        customerInfo.addCell(createCellDataInFo(name).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Địa Chỉ").setFont(font));
        customerInfo.addCell(createCellDataInFo(address).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Email").setFont(font));
        customerInfo.addCell(createCellDataInFo(email).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Số Điện Thoại").setFont(font));
        customerInfo.addCell(createCellDataInFo(phone).setFont(font));

        // Add more rows as needed

        return customerInfo;
    }


    private static Table createServiceUsageTable(HoaDonThanhToanRequest hoaDonThanhToanRequest,PdfFont font) {
        Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}));
        serviceTable.setWidth(UnitValue.createPercentValue(100));

        // Table Header
        serviceTable.addHeaderCell(createHeaderCell("Tên Dịch Vụ").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Số Lượng").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Giá").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Tổng").setFont(font));

        // Sample Service Data
        serviceTable.addCell(createCellData("Nước Uống").setFont(font));
        serviceTable.addCell(createCellData("5").setFont(font));
        serviceTable.addCell(createCellData("500.000").setFont(font));
        serviceTable.addCell(createCellData("250.000").setFont(font));

        // Sample Service Data
        serviceTable.addCell(createCellData("Áo Đấu").setFont(font));
        serviceTable.addCell(createCellData("5").setFont(font));
        serviceTable.addCell(createCellData("500.000").setFont(font));
        serviceTable.addCell(createCellData("250.000").setFont(font));

        serviceTable.addCell(createTotalCell("Tổng Tiền", String.valueOf(hoaDonThanhToanRequest.getTongTienSanCa())).setFont(font));

        return serviceTable;
    }

    private static Cell createCell(String header, String content) {
        return new Cell().add(new Paragraph(header).setFontSize(20))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph(content).setFontSize(20));
    }

    // header thông tin khách hàng
    private static Cell createHeaderCellInFo(String text) {
        return new Cell().add(new Paragraph(text))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(13);
    }

    // data thông tin khách hàng
    private static Cell createCellDataInFo(String data) {
        return new Cell().add(new Paragraph(data))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(13);
    }

    // header thông tin dịch vụ
    private static Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text)).
                setBorderBottom(new SolidBorder(DeviceGray.BLACK, 1))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14);
    }

    // data thông tin dịch vụ
    private static Cell createCellData(String data) {
        return new Cell().add(new Paragraph(data))
                .setBorderBottom(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(13);
    }

//    Tổng tiền 1
    private static Cell createTotalCell(String header, String total) {
        Cell cell = new Cell(1, 4).add(new Paragraph(header))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor( new DeviceRgb(63, 169, 219))
                .setFontSize(15);

        cell.add(new Paragraph(total));

        return cell;
    }

//     Tổng tiền 2

//
    private static Paragraph createFieldAndShiftInfo(String fieldInfo, String title) {
        Paragraph info = new Paragraph()
                .add(new Paragraph(fieldInfo).setFontSize(13))
                .add(new Paragraph(title).setFontSize(13))
                .setFontColor(new DeviceRgb(Color.BLACK))
                .setMarginBottom(2);
        return info;
    }


    private static Paragraph createClosingMessage(String text) {
        return new Paragraph(text)
                .setFontSize(15)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(10);
    }
}



