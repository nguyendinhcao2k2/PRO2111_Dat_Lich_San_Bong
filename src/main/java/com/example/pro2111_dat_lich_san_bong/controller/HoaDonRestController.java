package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("/*")
@RestController
@RequestMapping("/admin/hoa-don")
public class HoaDonRestController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping
    public ResponseEntity<List<HoaDon>> getAllHoaDons() {
        return ResponseEntity.ok(hoaDonService.getAllHoaDons());
    }
}
