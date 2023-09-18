package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.DichVu;
import com.example.pro2111_dat_lich_san_bong.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("/*")
@RestController
@RequestMapping("api/v1/admin/dich-vu")
public class DichVuRestController {
    @Autowired
    private DichVuService dichVuService;

    @GetMapping("/all")
    public ResponseEntity<List<DichVu>> getAllDichVus() {
        return ResponseEntity.ok(dichVuService.getAllDichVu());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<DichVu> getOneDichVu(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(dichVuService.getOneDichVu(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createNewDichVu(@RequestBody DichVu dichVu) {
        return ResponseEntity.ok(dichVuService.createNewDichVu(dichVu));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateDichVu(@PathVariable(name = "id") String id, @RequestBody DichVu dichVu) {
        return ResponseEntity.ok(dichVuService.updateDichVu(id, dichVu));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteDichVu(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(dichVuService.deleteDichVu(id));
    }
}
