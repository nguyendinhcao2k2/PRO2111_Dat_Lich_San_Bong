package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LoadSanBongRespose;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDatSanStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/v1/staff")
public class DatLichStaffRescontroller {

    @Autowired
    private IDatSanStaffService iDatSanStaffService;

    @GetMapping("/load-san-bong")
    public ResponseEntity<List<LoadSanBongRespose>> loadSanBong() {
        return ResponseEntity.ok(iDatSanStaffService.loadSanBong());
    }
}
