package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HinhThucThanhToan;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiPhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.repository.HinhThucThanhToanRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/api/v1/staff/thanh-toan")
public class ThanhToanHoaDonStaffController {
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private ThanhToanSanCaStaffServiceImpl thanhToanStaffService;
    @Autowired
    private HoaDonSanCaReponsitory hoaDonSanCaReponsitory;

    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;

    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;

    @GetMapping("/view-hoa-don")
    public String viewThanhToanHoaDon(Model model) {
        return "staff/danh-sach-hoa-don";
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public String getOneThanhToanHoaDon() {
        return "staff/thanh-toan-staff";
    }

    @GetMapping("/chuyen-khoan-thanh-cong/{id}")
    public String handlePaymentSuccess(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        if (paymentStatus != 1) {
            return "DemoVNPay/FailOder";
        }

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        Double giaTien = Double.valueOf(totalPrice.substring(0, totalPrice.length() - 2));

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", formatCurrency(giaTien));
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        Thread thread = new Thread(() -> {
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                HoaDonSanCa hoaDonSanCa = hoaDonSanCaReponsitory.findById(id).orElse(null);

                if (hoaDonSanCa != null && (hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal() || hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal())) {
                    hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal());
                    hoaDonSanCa.setNgayThanhToan(timestamp);
                    hoaDonSanCa.setTongTienHoaDonSanCa(giaTien);
                    thanhToanStaffService.saveViTienCocAndLichSu(hoaDonSanCa);
                    hoaDonSanCaReponsitory.saveAndFlush(hoaDonSanCa);

                    SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.getIdSanCa()).orElse(null);

                    if (sanCa != null) {
                        sanCa.setTrangThai(TrangThaiSanCa.QUA_GIO_HIEN_TAI.ordinal());
                        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                        hinhThucThanhToan.setLoaiHinhThanhToan(LoaiHinhThanhToan.CHUYEN_KHOAN);
                        hinhThucThanhToan.setTrangThai(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());
                        hinhThucThanhToan.setIdHoaDonSanCa(hoaDonSanCa.getId());
                        sanCaStaffRepository.saveAndFlush(sanCa);
                        hinhThucThanhToanRepository.saveAndFlush(hinhThucThanhToan);

                        List<PhuPhiHoaDon> listPhuPhiHoaDon = phuPhiHoaDonStaffRepository.findPhuPhiHoaDonsByIdHoaDonSanCa(hoaDonSanCa.getId());
                        listPhuPhiHoaDon.forEach(phuPhiHoaDon -> {
                            phuPhiHoaDon.setTrangThai(TrangThaiPhuPhiHoaDon.Da_Tra.ordinal());
                            phuPhiHoaDonStaffRepository.saveAndFlush(phuPhiHoaDon);
                        });

                        List<DichVuSanBong> listDichVuSanBong = dichVuSanBongStaffRepository.findAllByIdHoaDonSanCaAndTrangThai(hoaDonSanCa.getId(), TrangThaiDichVu.Dang_Su_Dung.ordinal());
                        listDichVuSanBong.forEach(dichVuSanBong -> {
                            dichVuSanBong.setTrangThai(TrangThaiDichVu.NGUNG_Su_Dung.ordinal());
                            dichVuSanBongStaffRepository.saveAndFlush(dichVuSanBong);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        return "DemoVNPay/SuccessOder";
    }


    public static String formatCurrency(double amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        Currency currency = Currency.getInstance(localeVN);
        currencyFormatter.setCurrency(currency);
        return currencyFormatter.format(amount);
    }
}
