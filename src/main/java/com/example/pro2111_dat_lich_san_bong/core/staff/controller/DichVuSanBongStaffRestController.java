package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DoThueNuocUongDichVuRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/dich-vu-san-bong")
public class DichVuSanBongStaffRestController {
    @Autowired
    private IDichVuSanBongStaffService dichVuSanBongStaffService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<List<DichVuSanBong>> getAllDichVuSanBongs() {
        List<DichVuSanBong> listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai("1", 0);
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    @GetMapping("/dich-vu-thanh-toan/{id}")
    public ResponseEntity<List<DichVuSanBongRequest>> getAllDichVuSanBongRequests(@PathVariable("id") String id) {
        List<DichVuSanBongRequest> listDichVuSanBongs = dichVuSanBongStaffService.dichVuSanBongSuDungByHoaDonSanCas(id, 0);
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    @GetMapping("/danh-sach-dich-vu")
    public ResponseEntity<List<DoThueNuocUongDichVuRequest>> getAllDoThueNuocUongDichVuRequest() {
        List<DoThueNuocUongDichVuRequest> listDoThueNuocUongDichVuRequest = dichVuSanBongStaffService.getAllDoThueNuocUongDichVuRequest(0);
        return ResponseEntity.ok(listDoThueNuocUongDichVuRequest);
    }
}
