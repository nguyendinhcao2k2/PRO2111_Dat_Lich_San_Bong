package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.HoaDonAdminService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/hoa-don")
public class HoaDonAdminRestController {

    @Autowired
    private HoaDonAdminService hoaDonAdminService;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, hoaDonAdminService.getAll()));
    }
}
