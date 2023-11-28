package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.LuatSanResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LuatSanAdminService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class LuatSanFindHomeRestController {

    @Autowired
    private LuatSanAdminService luatSanAdminService;

    @GetMapping("/find-luat-san")
    public ResponseEntity<?> getLuatSan() {
        try {
            LuatSanResponse luatSanResponse = luatSanAdminService.getAll().get(0);
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, luatSanResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.BAD_REQUEST, null));
        }

    }

}
