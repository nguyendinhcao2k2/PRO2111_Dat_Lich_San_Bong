package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.GiaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.GiaoCaStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/giao-ca")
public class GiaoCaRestController {

    @Autowired
    private GiaoCaStaffService giaoCaStaffService;

    @PostMapping("/khoi-tao-ca-lam")
    public ResponseEntity<?> khoiTaoCaLam(@RequestBody GiaoCaRequest giaoCaRequest) {
        giaoCaStaffService.khoiTaoCaLam(giaoCaRequest);
        return ResponseEntity.ok(new BaseResponse<GiaoCaRequest>(HttpStatus.OK, "Khoi tao ca thanh cong", giaoCaRequest));
    }

}
