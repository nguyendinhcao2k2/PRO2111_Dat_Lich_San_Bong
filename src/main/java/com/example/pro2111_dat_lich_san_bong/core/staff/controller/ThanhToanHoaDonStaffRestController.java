package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.ThanhToanRequets;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.SanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HinhThucThanhToan;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.SanBong;
import com.example.pro2111_dat_lich_san_bong.entity.SanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiPhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayConfig;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.vnpay.VNPayService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.example.pro2111_dat_lich_san_bong.repository.HinhThucThanhToanRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanBongRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class ThanhToanHoaDonStaffRestController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @Autowired
    private ThanhToanSanCaStaffServiceImpl thanhToanStaffService;
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;

    @Autowired
    private SanCaStaffRepository sanCaStaffRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;
    @Autowired
    private SanBongRepository sanBongRepository;
    @Autowired
    private PhuPhiStaffRepository phuPhiStaffRepository;

    @GetMapping("/get-all-thanh-toan")
    public ResponseEntity<List<HoaDonThanhToanRequest>> getAllHoaDonThanhToans() {
        try {
            List<HoaDonThanhToanRequest> listHoaDonThanhToans = thanhToanStaffService.getAllHoaDonSanCas();
            return ResponseEntity.ok(listHoaDonThanhToans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public ResponseEntity<HoaDonThanhToanRequest> getOneHoaDonThanhToan(@PathVariable(name = "id") String id) {
        try {
            HoaDonThanhToanRequest hoaDonThanhToanRequests = thanhToanStaffService.getOneHoaDonThanhToan(id);
            if (hoaDonThanhToanRequests != null) {
                return ResponseEntity.ok(hoaDonThanhToanRequests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/thanh-toan-tien-mat/{id}")
    public String checkPaymentMethod(
            @PathVariable(name = "id") String id,
            @RequestParam String paymentMethod,
            @RequestParam Double tongTienSave
    ) {
        try {
            if ("tienMat".equals(paymentMethod) && !id.isEmpty() && !id.isBlank()) {
                HoaDonSanCa hoaDonSanCa = hoaDonSanCaStaffRepository.findById(id).orElse(null);
                if (hoaDonSanCa != null && (hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.DA_CHECK_IN.ordinal() || hoaDonSanCa.getTrangThai() == TrangThaiHoaDonSanCa.CHUA_THANH_TOAN.ordinal())) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal());
                    hoaDonSanCa.setNgayThanhToan(timestamp);
                    hoaDonSanCa.setTongTienHoaDonSanCa(tongTienSave);
                    hoaDonSanCaStaffRepository.saveAndFlush(hoaDonSanCa);
                    SanCa sanCa = sanCaStaffRepository.findById(hoaDonSanCa.getIdSanCa()).orElse(null);
                    if (sanCa != null) {
                        sanCa.setTrangThai(TrangThaiSanCa.QUA_GIO_HIEN_TAI.ordinal());
                        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                        hinhThucThanhToan.setLoaiHinhThanhToan(LoaiHinhThanhToan.TIEN_MAT);
                        hinhThucThanhToan.setTrangThai(LoaiHinhThanhToan.TIEN_MAT.ordinal());
                        hinhThucThanhToan.setIdHoaDonSanCa(hoaDonSanCa.getId());
                        sanCaStaffRepository.saveAndFlush(sanCa);
                        thanhToanStaffService.saveViTienCocAndLichSu(hoaDonSanCa);
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
                    return paymentMethod;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentMethod;
    }

    @PostMapping("/thanh-toan-chuyen-khoan")
    public String submidOrder(@RequestParam("idHoaDonSanCa") String id, @RequestParam("amount") int orderTotal, @RequestParam("orderInfo") String orderInfo, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        baseUrl += "/api/v1/staff/thanh-toan/chuyen-khoan-thanh-cong/" + id;
        int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl, miuteParam);
        return vnpayUrl;
    }

    @GetMapping("/search-by-phone")
    public ResponseEntity<List<HoaDonThanhToanRequest>> getAllHoaDonThanhToanBySoDienThoai(@RequestParam(name = "numberPhone") String numberPhone) {
        List<HoaDonThanhToanRequest> listHoaDonThanhToanRequests = thanhToanStaffService.getAllHoaDonSanCaByPhone(numberPhone);
        return ResponseEntity.ok(listHoaDonThanhToanRequests);
    }

    @PostMapping("/thanh-toan-hoa-don-nhieu")
    public ResponseEntity<ThanhToanRequets> processItems(@RequestBody List<String> selectedItems) {
        ThanhToanRequets thanhToanRequets = new ThanhToanRequets();
        try {
            for (String selectedItem : selectedItems) {
                processHoaDon(selectedItem, thanhToanRequets);
            }
            return ResponseEntity.ok(thanhToanRequets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(thanhToanRequets);
    }

    private void processHoaDon(String selectedItem, ThanhToanRequets thanhToanRequets) {
        try {
            HoaDonThanhToanRequest hoaDonThanhToanRequest = thanhToanStaffService.getOneHoaDonThanhToan(selectedItem);

            thanhToanRequets.setTenKhachHang(hoaDonThanhToanRequest.getTenKhachHang());
            thanhToanRequets.setSoDienThoai(hoaDonThanhToanRequest.getSoDienThoai());
            thanhToanRequets.setTenSanBong(hoaDonThanhToanRequest.getTenSanBong());
            thanhToanRequets.setTenLoaiSan(hoaDonThanhToanRequest.getTenLoaiSan());
            thanhToanRequets.setTenCa(hoaDonThanhToanRequest.getTenCa());
            thanhToanRequets.setThoiGianBatDau(hoaDonThanhToanRequest.getThoiGianBatDau());
            thanhToanRequets.setThoiGianKetThuc(hoaDonThanhToanRequest.getThoiGianKetThuc());
            thanhToanRequets.setTongTienCoc(handleNull(thanhToanRequets.getTongTienCoc()) + hoaDonThanhToanRequest.getTienCoc());
            thanhToanRequets.setTongTienCocThua(handleNull(thanhToanRequets.getTongTienCocThua()) + hoaDonThanhToanRequest.getTienCocThua());
            thanhToanRequets.setTongTienSanBong(handleNull(thanhToanRequets.getTongTienSanBong()) + hoaDonThanhToanRequest.getTongTienSanCa());

            double tongTienDichVu = calculateTongTienDichVu(selectedItem);
            thanhToanRequets.setTongTienDichVu(handleNull(thanhToanRequets.getTongTienDichVu()) + tongTienDichVu);

            double tongTienPhuPhi = calculateTongTienPhuPhi(selectedItem);
            thanhToanRequets.setTongTienPhuPhi(handleNull(thanhToanRequets.getTongTienPhuPhi()) + tongTienPhuPhi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calculateTongTienDichVu(String hoaDonId) {
        List<DichVuSanBongRequest> listDichVuSanBongs = dichVuSanBongStaffRepository.dichVuSanBongSuDungByHoaDonSanCas(hoaDonId, TrangThaiDichVu.Dang_Su_Dung.ordinal());
        double tongTienDichVu = 0;
        try {
            if (listDichVuSanBongs != null && !listDichVuSanBongs.isEmpty()) {
                for (DichVuSanBongRequest dichVu : listDichVuSanBongs) {
                    tongTienDichVu += dichVu.getTongTien();
                }
            }
            return tongTienDichVu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongTienDichVu;
    }

    private double calculateTongTienPhuPhi(String hoaDonId) {
        List<PhuPhiHoaDon> listPhuPhiHoaDons = phuPhiHoaDonStaffRepository.findPhuPhiHoaDonsByIdHoaDonSanCa(hoaDonId);
        double tongTienPhuPhi = 0;
        try {
            if (listPhuPhiHoaDons != null && !listPhuPhiHoaDons.isEmpty()) {
                for (PhuPhiHoaDon phuPhiHoaDon : listPhuPhiHoaDons) {
                    PhuPhi phuPhi = phuPhiStaffRepository.findById(phuPhiHoaDon.getIdPhuPhi()).orElse(null);
                    if (phuPhiHoaDon.getIdPhuPhi().equals(phuPhi.getId())) {
                        tongTienPhuPhi += phuPhi.getGiaPhuPhi();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongTienPhuPhi;
    }


    @PostMapping("/thanh-toan-tien-mat-nhieu")
    public ResponseEntity<String> thanhToanTienMatNhieuHoaDon(
            @RequestParam(name = "paymentMethod") String paymentMethod,
            @RequestParam(name = "listId") String listIdHoaDonSanCa) {
        try {
            List<HoaDonSanCa> listHoaDonSanCa = hoaDonSanCaStaffRepository.findAll();
            List<String> listThanhToans = Arrays.asList(listIdHoaDonSanCa.split(","));
            if (paymentMethod.equals("tienMat2")) {
                for (int i = 0; i < listThanhToans.size(); i++) {
                    System.out.println(listThanhToans.get(i));
                    for (int j = 0; j < listHoaDonSanCa.size(); j++) {
                        if ((listHoaDonSanCa.get(j).getId()).equals(listThanhToans.get(i))) {
                            HoaDonSanCa hoaDonSanCa = hoaDonSanCaStaffRepository.findById(listHoaDonSanCa.get(j).getId()).get();
                            HoaDonThanhToanRequest hoaDonThanhToanRequest = thanhToanStaffService.getOneHoaDonThanhToan(listHoaDonSanCa.get(j).getId());
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
                }
            }
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Bug");
    }

    @PostMapping("/thanh-toan-chuyen-khoan-nhieu-hoa-don")
    public String chuyenKhoanNhieuHoaDon(@RequestParam("amount") int orderTotal, @RequestParam("orderInfo") String orderInfo, HttpServletRequest request) {
        try {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            baseUrl += "/api/v1/staff/thanh-toan/chuyen-khoan-thanh-cong-nhieu-hoa-don";
            int miuteParam = Integer.valueOf(sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_HET_GD).getValue());
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl, miuteParam);
            return vnpayUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "Bug";
        }
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
