package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.HoaDonThanhToanRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class ThanhToanHoaDonStaffRestController {

    @Autowired
    private ThanhToanSanCaStaffServiceImpl thanhToanStaffService;


    @GetMapping("/get-all-thanh-toan")
    public ResponseEntity<List<HoaDonThanhToanRequest>> getAllHoaDonThanhToans() {
        List<HoaDonThanhToanRequest> listHoaDonThanhToans = thanhToanStaffService.getAllHoaDonSanCas(1);
        return ResponseEntity.ok(listHoaDonThanhToans);
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public ResponseEntity<HoaDonThanhToanRequest> getOneHoaDonThanhToan(@PathVariable(name = "id") String id) {
        HoaDonThanhToanRequest hoaDonThanhToanRequests = thanhToanStaffService.getOneHoaDonThanhToan(id);
        return ResponseEntity.ok(hoaDonThanhToanRequests);
    }
}
