package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HinhThucThanhToan;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


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
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;
    @Autowired
    private PhuPhiStaffRepository phuPhiStaffRepository;

    @GetMapping("/view-hoa-don")
    public String viewThanhToanHoaDon(Model model) {
        return "staff/danh-sach-hoa-don";
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public String getOneThanhToanHoaDon() {
        return "staff/thanh-toan-staff";
    }

    private double calculateTongTienDichVu(String hoaDonId) {
        try {
            List<DichVuSanBongRequest> listDichVuSanBongs = dichVuSanBongStaffRepository.dichVuSanBongSuDungByHoaDonSanCas(hoaDonId, TrangThaiDichVu.Dang_Su_Dung.ordinal());
            double tongTienDichVu = 0;
            if (listDichVuSanBongs != null && !listDichVuSanBongs.isEmpty()) {
                for (DichVuSanBongRequest dichVu : listDichVuSanBongs) {
                    tongTienDichVu += dichVu.getTongTien();
                }
            }

            return tongTienDichVu;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double calculateTongTienPhuPhi(String hoaDonId) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @GetMapping("/chuyen-khoan-thanh-cong/{id}")
    public String handlePaymentSuccess(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        try {
            int paymentStatus = vnPayService.orderReturn(request);

            if (paymentStatus != 1) {
                return "staff/fail-order-staff";
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
                            capNhatTrangThaiDichVu(hoaDonSanCa.getId());
                            capNhatTrangThaiPhuPhi(hoaDonSanCa.getId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
            return "staff/success-oder";
        } catch (Exception e) {
            e.printStackTrace();
            return "staff/fail-order-staff";
        }
    }


    public static String formatCurrency(double amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        Currency currency = Currency.getInstance(localeVN);
        currencyFormatter.setCurrency(currency);
        return currencyFormatter.format(amount);
    }

    @GetMapping("/chuyen-khoan-thanh-cong-nhieu-hoa-don")
    public String chuyenKhoanThanhCongNhieuHoaDon(@CookieValue(value = "listThanhToans", defaultValue = "") String listThanhToans, HttpServletRequest request, Model model) {
        List<String> listString = new ArrayList<String>();
        try {
            List<String> listIdHoaDonNew = Arrays.stream(listThanhToans.replaceAll("[\\[\\]\"]", "").split(", "))
                    .map(String::trim)
                    .collect(Collectors.toList());

            for (String uuid : listIdHoaDonNew) {
                listString = Arrays.asList(uuid.split(","));
            }

            int paymentStatus = vnPayService.orderReturn(request);
            if (paymentStatus != 1) {
                return "staff/fail-order-staff";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> finalListString = listString;
        Thread thread = new Thread(() -> {
            try {
                for (String uuid : finalListString) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    HoaDonSanCa hoaDonSanCa = hoaDonSanCaReponsitory.findById(uuid).orElse(null);
                    HoaDonThanhToanRequest hoaDonThanhToanRequest = thanhToanStaffService.getOneHoaDonThanhToan(uuid);
                    if (hoaDonSanCa != null && (hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal() || hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal())) {
                        double tienSan = handleNull(hoaDonSanCa.getTienSan());
                        double tienCocThua = handleNull(hoaDonSanCa.getTienCocThua());
                        double tienCoc = handleNull(hoaDonThanhToanRequest.getTienCoc());
                        double tongTienDichVu = handleNull(calculateTongTienDichVu(hoaDonSanCa.getId()));
                        double tongTienPhuPhi = handleNull(calculateTongTienPhuPhi(hoaDonSanCa.getId()));
                        double tongThanhToan = (tienSan + tongTienDichVu + tongTienPhuPhi) - (tienCoc + tienCocThua);
                        hoaDonSanCa.setTongTienHoaDonSanCa(tongThanhToan);
                        hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal());
                        hoaDonSanCaStaffRepository.saveAndFlush(hoaDonSanCa);
                        //HÌNH THỨC THANH TOÁN
                        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                        hinhThucThanhToan.setLoaiHinhThanhToan(LoaiHinhThanhToan.CHUYEN_KHOAN);
                        hinhThucThanhToan.setTrangThai(LoaiHinhThanhToan.CHUYEN_KHOAN.ordinal());
                        hinhThucThanhToan.setIdHoaDonSanCa(hoaDonSanCa.getId());
                        hinhThucThanhToanRepository.saveAndFlush(hinhThucThanhToan);
                        SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.getIdSanCa()).orElse(null);
                        if (sanCa != null) {
                            sanCa.setTrangThai(TrangThaiSanCa.QUA_GIO_HIEN_TAI.ordinal());
                            sanCaStaffRepository.saveAndFlush(sanCa);
                            thanhToanStaffService.saveViTienCocAndLichSu(hoaDonSanCa);
                            hinhThucThanhToanRepository.saveAndFlush(hinhThucThanhToan);
                            capNhatTrangThaiDichVu(hoaDonSanCa.getId());
                            capNhatTrangThaiPhuPhi(hoaDonSanCa.getId());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();
        return "staff/success-oder";
    }

    private void capNhatTrangThaiDichVu(String idHoaDonSanCa) {
        try {
            List<PhuPhiHoaDon> listPhuPhiHoaDon = phuPhiHoaDonStaffRepository.findPhuPhiHoaDonsByIdHoaDonSanCa(idHoaDonSanCa);
            listPhuPhiHoaDon.forEach(phuPhiHoaDon -> {
                phuPhiHoaDon.setTrangThai(TrangThaiPhuPhiHoaDon.Da_Tra.ordinal());
                phuPhiHoaDonStaffRepository.saveAndFlush(phuPhiHoaDon);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void capNhatTrangThaiPhuPhi(String idHoaDonSanCa) {
        try {
            List<DichVuSanBong> listDichVuSanBong = dichVuSanBongStaffRepository.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, TrangThaiDichVu.Dang_Su_Dung.ordinal());
            listDichVuSanBong.forEach(dichVuSanBong -> {
                dichVuSanBong.setTrangThai(TrangThaiDichVu.NGUNG_Su_Dung.ordinal());
                dichVuSanBongStaffRepository.saveAndFlush(dichVuSanBong);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double handleNull(Double value) {
        return (value == null) ? 0.0 : value;
    }

}
