package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.entity.HinhThucThanhToan;
import com.example.pro2111_dat_lich_san_bong.repository.HinhThucThanhToanRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/hinh-thuc-thanh-toan")
public class HinhThucThanhToanRestController {
    @Autowired
    private HinhThucThanhToanRepository repository;

    @GetMapping("/get-all")
    public ResponseEntity<List<HinhThucThanhToan>> getAll() {
        List<HinhThucThanhToan> listHinhThucThanhToan = repository.findAll();
        return ResponseEntity.ok(listHinhThucThanhToan);
    }

    @PostMapping("/save")
    public ResponseEntity<HinhThucThanhToan> save(@RequestBody HinhThucThanhToan hinhThucThanhToan) {
        return ResponseEntity.ok(repository.save(hinhThucThanhToan));
    }

    @PutMapping("/update")
    public ResponseEntity<HinhThucThanhToan> update(@RequestBody HinhThucThanhToan hinhThucThanhToan) {
        HinhThucThanhToan hinhThucThanhToanSave = repository.saveAndFlush(hinhThucThanhToan);
        return ResponseEntity.ok(hinhThucThanhToanSave);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
