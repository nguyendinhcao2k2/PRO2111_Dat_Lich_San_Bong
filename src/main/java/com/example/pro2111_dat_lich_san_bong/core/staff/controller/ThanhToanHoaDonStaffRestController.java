package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.repository.DichVuSanBongRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class ThanhToanHoaDonStaffRestController {

    @Autowired
    private ThanhToanSanCaStaffServiceImpl thanhToanStaffService;
    @Autowired
    private HoaDonSanCaReponsitory hoaDonSanCaReponsitory;

    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;

    @GetMapping("/get-all-thanh-toan")
    public ResponseEntity<List<HoaDonThanhToanRequest>> getAllHoaDonThanhToans() {
        List<HoaDonThanhToanRequest> listHoaDonThanhToans = thanhToanStaffService.getAllHoaDonSanCas();
        return ResponseEntity.ok(listHoaDonThanhToans);
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public ResponseEntity<HoaDonThanhToanRequest> getOneHoaDonThanhToan(@PathVariable(name = "id") String id) {
        HoaDonThanhToanRequest hoaDonThanhToanRequests = thanhToanStaffService.getOneHoaDonThanhToan(id);
        return ResponseEntity.ok(hoaDonThanhToanRequests);
    }

    @PostMapping("/thanh-toan/{id}")
    public String checkPaymentMethod(@PathVariable(name = "id") String id, @RequestBody String paymentMethod) {
        System.out.println(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (paymentMethod.equals("paymentMethod=tienMat") && !id.isEmpty() && !id.isBlank()) {
            HoaDonSanCa hoaDonSanCa = hoaDonSanCaReponsitory.getById(id);
            hoaDonSanCa.setTrangThai(TrangThaiHoaDonSanCa.DA_THANH_TOAN.ordinal());
            hoaDonSanCa.setNgayThanhToan(timestamp);
            hoaDonSanCaReponsitory.save(hoaDonSanCa);
            List<DichVuSanBong> listDichVuSanBong = dichVuSanBongStaffRepository.findAllByIdHoaDonSanCaAndTrangThai(hoaDonSanCa.getId(), TrangThaiDichVu.Dang_Su_Dung.ordinal());
            for (DichVuSanBong dichVuSanBong : listDichVuSanBong) {
                dichVuSanBong.setTrangThai(TrangThaiDichVu.NGUNG_Su_Dung.ordinal());
                dichVuSanBongStaffRepository.save(dichVuSanBong);
            }
            return paymentMethod;
        }
        return paymentMethod;
    }
}
