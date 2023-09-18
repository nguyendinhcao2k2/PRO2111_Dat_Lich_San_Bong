package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.ThanhToan;
import com.example.pro2111_dat_lich_san_bong.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("/*")
@RestController
@RequestMapping("api/v1/admin/thanh-toan")
public class ThanhToanRestController {
    @Autowired
    private ThanhToanService thanhToanService;

    @GetMapping("/all")
    public ResponseEntity<List<ThanhToan>> getAllThanhToans() {
        return ResponseEntity.ok(thanhToanService.getAllThanhToan());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ThanhToan> getOneThanhToan(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(thanhToanService.getOneThanhToan(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createNewThanhToan(@RequestBody ThanhToan thanhToan) {
        return ResponseEntity.ok(thanhToanService.createNewThanhToan(thanhToan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateThanhToan(@PathVariable(name = "id") String id, @RequestBody ThanhToan thanhToan) {
        return ResponseEntity.ok(thanhToanService.updateThanhToan(id, thanhToan));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteThanhToan(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(thanhToanService.deleteThanhToan(id));
    }
}
