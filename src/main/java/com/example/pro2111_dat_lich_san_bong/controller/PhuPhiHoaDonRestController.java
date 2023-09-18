package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.service.PhuPhiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("/*")
@RestController
@RequestMapping("api/v1/admin/phu-phi-hoa-don")
public class PhuPhiHoaDonRestController {
    @Autowired
    private PhuPhiHoaDonService phuPhiHoaDonService;

    @GetMapping("/all")
    public ResponseEntity<List<PhuPhiHoaDon>> getAllPhuPhiHoaDons() {
        return ResponseEntity.ok(phuPhiHoaDonService.getAllPhuPhiHoaDons());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<PhuPhiHoaDon> getOnePhuPhiHoaDon(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(phuPhiHoaDonService.getPhuPhiHoaDonsById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createNewPhuPhiHoaDon(@RequestBody PhuPhiHoaDon phuPhiHoaDon) {
        return ResponseEntity.ok(phuPhiHoaDonService.createNewPhuPhiHoaDon(phuPhiHoaDon));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updatePhuPhiHoaDon(@PathVariable(name = "id") String id, @RequestBody PhuPhiHoaDon phuPhiHoaDon) {
        return ResponseEntity.ok(phuPhiHoaDonService.updatePhuPhiHoaDon(id, phuPhiHoaDon));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePhuPhiHoaDon(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(phuPhiHoaDonService.deletePhuPhiHoaDon(id));
    }

}
