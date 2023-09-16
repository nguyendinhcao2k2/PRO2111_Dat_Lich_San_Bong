package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin("/*")
@RestController
@RequestMapping("/admin/hoa-don")
public class HoaDonRestController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/get-list")
    public ResponseEntity<List<HoaDon>> getAllHoaDons() {
        return ResponseEntity.ok(hoaDonService.getAllHoaDons());
    }

    @PostMapping("/create-hoa-don")
    public ResponseEntity<Boolean> createHoaDon(@RequestBody HoaDon hoaDon) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        hoaDon.setNgayThanhToan(currentTimestamp);
        Boolean check = hoaDonService.createNewHoaDon(hoaDon);
        return ResponseEntity.ok(check);
    }

    @PutMapping("/update-hoa-don/{id}")
    public ResponseEntity<Boolean> updateHoaDon(@PathVariable(name = "id") String id, @RequestBody HoaDon hoaDon) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        hoaDon.setNgayThanhToan(currentTimestamp);
        Boolean check = hoaDonService.createNewHoaDon(hoaDon);
        return ResponseEntity.ok(check);
    }
}
