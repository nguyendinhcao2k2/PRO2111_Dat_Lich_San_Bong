package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonThanhToan;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api-v1-admin/hoa-don-thanh-toan")
public class HoDonThanhToanRestController {
    @Autowired
    private HoaDonThanhToanService hoaDonThanhToanService;

    @GetMapping("/all")
    public ResponseEntity<List<HoaDonThanhToan>> getAllHoaDonThanhToans() {
        return ResponseEntity.ok(hoaDonThanhToanService.getAllHoaDonThanhToans());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<HoaDonThanhToan> getOneHoaDonThanhToan(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(hoaDonThanhToanService.getOneHoaDonThanhToan(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createNewHoaDonThanhToan(@RequestBody HoaDonThanhToan hoaDonThanhToan) {
        return ResponseEntity.ok(hoaDonThanhToanService.createNewHoaDonThanhToan(hoaDonThanhToan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateHoaDonThanhToan(@PathVariable(name = "id") String id, @RequestBody HoaDonThanhToan hoaDonThanhToan) {
        return ResponseEntity.ok(hoaDonThanhToanService.updateHoaDonThanhToan(id, hoaDonThanhToan));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteHoaDonThanhToan(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(hoaDonThanhToanService.deleteHoaDonThanhToan(id));
    }
}
