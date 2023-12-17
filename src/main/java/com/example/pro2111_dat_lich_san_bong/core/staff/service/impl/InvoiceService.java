package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.AccountStaffReponsitoty;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDoThueStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.INuocUongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class InvoiceService {
    @Autowired
    private IDichVuSanBongStaffService dichVuSanBongStaffService;

    @Autowired
    private HoaDonSanCaReponsitory hoaDonSanCaReponsitory;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private INuocUongStaffService nuocUongStaffService;
    @Autowired
    private IDoThueStaffService doThueStaffService;
    @Autowired
    private PhuPhiStaffRepository phuPhiStaffRepository;
    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;
    @Autowired
    private AccountStaffReponsitoty accountStaffReponsitoty;

    public byte[] generatePdfMotHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNThoiNay_R.TTF", "Identity-H", true);
        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            HoaDonSanCa hoaDonSanCas = hoaDonSanCaReponsitory.findById(hoaDonThanhToanRequest.getId()).get();
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonSanCas.getIdHoaDon()).get();
            Account account = accountStaffReponsitoty.findAccountById(handleNull(hoaDon.getIdAccountConfirm()));
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
                    createFieldAndShiftInfo("Tên Nhân Viên : ", " " + account.getDisplayName())
                            .setFont(font)
                            .setTextAlignment(TextAlignment.RIGHT)
            );
            Date currentDate = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            String thoiGianBatDau = String.valueOf(hoaDonThanhToanRequest.getThoiGianBatDau());
            String thoiGianKetThuc = String.valueOf(hoaDonThanhToanRequest.getThoiGianKetThuc());
            document.add(createFieldAndShiftInfo("Thời Gian : ", dateFormat.format(currentDate))
                    .setFont(font)
                    .setTextAlignment(TextAlignment.RIGHT)
            );


            document.add(
                    createHeader("Thông Tin Khách Hàng")
                            .setFont(font)
                            .setFontSize(20)
                            .setBackgroundColor(new DeviceRgb(63, 169, 219))
            );
            document.add(
                    createCustomerInfo(
                            String.valueOf(hoaDonThanhToanRequest.getTenKhachHang()),
                            "Sân Bóng Đồng Đế", hoaDon.getEmail(),
                            String.valueOf(hoaDonThanhToanRequest.getSoDienThoai()),
                            String.valueOf(hoaDonThanhToanRequest.getTenSanBong() + " - " + hoaDonThanhToanRequest.getTenCa()),
                            hoaDonThanhToanRequest.getTienCoc(),
                            hoaDonThanhToanRequest.getTienCocThua()
                    )
            );


            document.add(new Paragraph("\n"));

            document.add(createHeader("Dịch Vụ Sử Dụng").setFont(font)
                    .setFontSize(20)
            );


            document.add(createServiceUsageTable(hoaDonThanhToanRequest, font));

            document.add(createClosingMessage("Xin Cảm Ơn Quý Khách! -- Liên Hệ: 0356169620.")
                    .setFont(font)
                    .setFontSize(15));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private Table createHeader(String name) {
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

    private Table createCustomerInfo(String name, String address, String email, String phone, String sanCa, double tienCocSan, double tienCocSanThua) throws IOException {
        PdfFont font = PdfFontFactory.createFont("src/main/resources/static/fontPDF/UVNThoiNay_R.TTF", "Identity-H", true);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Table customerInfo = new Table(UnitValue.createPercentArray(new float[]{2, 2}));
        customerInfo.setWidth(UnitValue.createPercentValue(100));

        customerInfo.addCell(createHeaderCellInFo("Tên Khách Hàng : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(name).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Số Điện Thoại : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(phone).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Tiền Cọc Sân : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(String.valueOf(currencyFormat.format(tienCocSan))).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Tiền Cọc Sân Thừa: ").setFont(font));
        customerInfo.addCell(createCellDataInFo(String.valueOf(currencyFormat.format(tienCocSanThua))).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Địa Chỉ : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(address).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Email : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(email).setFont(font));

        customerInfo.addCell(createHeaderCellInFo("Sân Ca Hóa Đơn : ").setFont(font));
        customerInfo.addCell(createCellDataInFo(sanCa).setFont(font));

        return customerInfo;
    }

    private String handleNull(String string) {
        return (string == "") ? "cec96064-f270-499f-9c05-13adbc0ab80a" : string;
    }

    private Table createServiceUsageTable(HoaDonThanhToanRequest hoaDonThanhToanRequest, PdfFont font) {
        Table serviceTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}));
        serviceTable.setWidth(UnitValue.createPercentValue(100));
        // Table Header
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        System.out.println(hoaDonThanhToanRequest.getId());
        List<DichVuSanBongRequest> listDichVuSanBongRequests = dichVuSanBongStaffService.dichVuSanBongSuDungByHoaDonSanCas(hoaDonThanhToanRequest.getId(), TrangThaiDichVu.Dang_Su_Dung.ordinal());

        serviceTable.addHeaderCell(createHeaderCell("Tên Dịch Vụ").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Số Lượng").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Giá").setFont(font));
        serviceTable.addHeaderCell(createHeaderCell("Tổng").setFont(font));
        double tongTienDichVu = 0;
        if (listDichVuSanBongRequests.size() > 0) {
            for (DichVuSanBongRequest dichVuSanBongRequest : listDichVuSanBongRequests) {
                tongTienDichVu += dichVuSanBongRequest.getTongTien();
                if (dichVuSanBongRequest.getIdNuocUong() != null) {
                    NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(dichVuSanBongRequest.getIdNuocUong());
                    int soLuong = Integer.valueOf(Math.toIntExact(dichVuSanBongRequest.getSoLuongNuocUong()));
                    Double donGia = nuocUong.getDonGia();
                    Double tongTien = soLuong * donGia;
                    serviceTable.addCell(createCellData(nuocUong.getTenNuocUong()).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(soLuong)).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(currencyFormat.format(donGia))).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(currencyFormat.format(tongTien))).setFont(font));
                }
                if (dichVuSanBongRequest.getGiaDoThue() != null) {
                    DoThue doThue = doThueStaffService.getOneDoThue(dichVuSanBongRequest.getIdDoThue());
                    int soLuong = Integer.valueOf(Math.toIntExact(dichVuSanBongRequest.getSoLuongDoThue()));
                    Double donGia = doThue.getDonGia();
                    Double tongTien = soLuong * donGia;
                    serviceTable.addCell(createCellData(doThue.getTenDoThue()).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(soLuong)).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(currencyFormat.format(donGia))).setFont(font));
                    serviceTable.addCell(createCellData(String.valueOf(currencyFormat.format(tongTien))).setFont(font));
                }
            }
        } else {
            tongTienDichVu = 0;
            serviceTable.addCell(createCellData("CHƯA SỬ DỤNG DỊCH VỤ!.").setFont(font));
            serviceTable.addCell(createCellData("0").setFont(font));
            serviceTable.addCell(createCellData("0").setFont(font));
            serviceTable.addCell(createCellData("0").setFont(font));
        }

        double tongTienPhuPhi = calculateTongTienPhuPhi(hoaDonThanhToanRequest.getId());
        serviceTable.addCell(createTotalCell("Tiền Dich Vụ : " + currencyFormat.format(tongTienDichVu) + " ; " + " Tiền Sân Bóng: " + currencyFormat.format(hoaDonThanhToanRequest.getTongTienSanCa()) + " ; " + " Tiền Phụ Phí: " + currencyFormat.format(tongTienPhuPhi), " ").setFont(font));
        double tongThanhToan = ((hoaDonThanhToanRequest.getTongTienSanCa() + tongTienDichVu + tongTienPhuPhi) - (hoaDonThanhToanRequest.getTienCoc() + hoaDonThanhToanRequest.getTienCocThua()));
        serviceTable.addCell(createTotalCell("Tổng Hóa Đơn Thanh Toán: " + currencyFormat.format(tongThanhToan), " ").setFont(font));

        return serviceTable;
    }

    private double calculateTongTienPhuPhi(String hoaDonId) {
        List<PhuPhiHoaDon> listPhuPhiHoaDons = phuPhiHoaDonStaffRepository.findPhuPhiHoaDonsByIdHoaDonSanCa(hoaDonId);
        double tongTienPhuPhi = 0;
        if (listPhuPhiHoaDons != null && !listPhuPhiHoaDons.isEmpty()) {
            for (PhuPhiHoaDon phuPhiHoaDon : listPhuPhiHoaDons) {
                PhuPhi phuPhi = phuPhiStaffRepository.findById(phuPhiHoaDon.getIdPhuPhi()).orElse(null);
                if (phuPhiHoaDon.getIdPhuPhi().equals(phuPhi.getId())) {
                    tongTienPhuPhi += phuPhi.getGiaPhuPhi();
                }
            }
        }
        return tongTienPhuPhi;
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
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(13);
    }

    // data thông tin khách hàng
    private static Cell createCellDataInFo(String data) {
        return new Cell().add(new Paragraph(data))
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.LEFT)
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
        Cell cell = new Cell(4, 4).add(new Paragraph(header))
                .setBorder(new SolidBorder(DeviceGray.BLACK, 1))
                .setTextAlignment(TextAlignment.RIGHT)
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



