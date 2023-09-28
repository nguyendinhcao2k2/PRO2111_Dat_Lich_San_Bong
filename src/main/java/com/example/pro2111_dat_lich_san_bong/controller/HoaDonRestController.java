package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/admin/hoa-don")
public class HoaDonRestController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/all")
    public ResponseEntity<List<HoaDon>> getAllHoaDons() {
        return ResponseEntity.ok(hoaDonService.getAllHoaDons());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<HoaDon> getOneHoaDon(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(hoaDonService.getOneHoaDon(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createHoaDon(@RequestBody HoaDon hoaDon) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        hoaDon.setNgayThanhToan(currentTimestamp);
        Boolean check = hoaDonService.createNewHoaDon(hoaDon);
        return ResponseEntity.ok(check);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateHoaDon(@PathVariable(name = "id") String id, @RequestBody HoaDon hoaDon) {
        Boolean check = hoaDonService.updateHoaDonById(id, hoaDon);
        return ResponseEntity.ok(check);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteHoaDon(@PathVariable(name = "id") String id) {
        Boolean check = hoaDonService.deleteHoaDonById(id);
        return ResponseEntity.ok(check);
    }
}
