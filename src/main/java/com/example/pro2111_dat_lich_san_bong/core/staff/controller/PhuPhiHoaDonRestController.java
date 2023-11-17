package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiHoaDonStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.PhuPhiStaffRepository;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiPhuPhi;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiPhuPhiHoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/phu-phi-hoa-don")
public class PhuPhiHoaDonRestController {
    @Autowired
    private PhuPhiHoaDonStaffRepository phuPhiHoaDonStaffRepository;
    @Autowired
    private PhuPhiStaffRepository phuPhiStaffRepository;

    @GetMapping("/get-by-id-hoa-don/{id}")
    public ResponseEntity<List<PhuPhiHoaDonRequest>> getPhuPhiHoaDonById(@PathVariable("id") String id) {
        System.out.println("GỌI THÀNH CÔNG");
        List<PhuPhiHoaDonRequest> listPhuPhiHoaDonRequests = phuPhiHoaDonStaffRepository.getPhuPhiHoaDonByIdSanCa(id, TrangThaiPhuPhiHoaDon.Chua_Tra.ordinal());
        return ResponseEntity.ok(listPhuPhiHoaDonRequests);
    }

    @PostMapping("/luu-phu-phi/{id}")
    public ResponseEntity<Boolean> savePhuPhiHoaDon(@PathVariable("id") String id, @RequestBody PhuPhiHoaDonRequest phuPhiHoaDonRequest) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String tenPhuPhi = phuPhiHoaDonRequest.getTenPhuPhi();
        Double giaPhuPhi = phuPhiHoaDonRequest.getGiaPhuPhi();
        PhuPhi phuPhi = new PhuPhi();
        phuPhi.setTenPhuPhi(tenPhuPhi);
        phuPhi.setGiaPhuPhi(giaPhuPhi);
        phuPhi.setTrangThai(TrangThaiPhuPhi.Co.ordinal());
        phuPhiStaffRepository.save(phuPhi);
        PhuPhiHoaDon phuPhiHoaDon = new PhuPhiHoaDon();
        phuPhiHoaDon.setId(null);
        phuPhiHoaDon.setIdHoaDonSanCa(id);
        phuPhiHoaDon.setIdPhuPhi(phuPhi.getId());
        phuPhiHoaDon.setThoiGianTao(currentTimestamp);
        phuPhiHoaDon.setTrangThai(TrangThaiPhuPhiHoaDon.Chua_Tra.ordinal());
        phuPhiHoaDonStaffRepository.save(phuPhiHoaDon);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/xoa-phu-phi/{id}")
    public ResponseEntity<Boolean> xoaPhuPhiHoaDon(@PathVariable("id") String id) {
        phuPhiHoaDonStaffRepository.deleteById(id);
        System.out.println("XÓA THÀNH CÔNG: " + id);
        return ResponseEntity.ok(true);
    }
}
