package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.KhoiTaoCaRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff/giao-ca")
public class GiaoCaStaffRestController {

    @Autowired
    private IGiaoCaStaffService _giaoCaStaffService;

    @Autowired
    private CommonSession _commonSession;

    @PostMapping("khoi-tao-ca-lam")
    public ResponseEntity<?> khoiTaoCaLam(@RequestBody KhoiTaoCaRequest khoiTaoCaRequest) {
        var check = _giaoCaStaffService.khoiTaoCaLamViec(khoiTaoCaRequest);
        if (check) {
            return ResponseEntity.ok(new BaseResponse<KhoiTaoCaRequest>(HttpStatus.OK, khoiTaoCaRequest));
        }
        return ResponseEntity.ok(new BaseResponse<String>(HttpStatus.BAD_GATEWAY, "Nhận ca thất bại!"));
    }

    @GetMapping("order-by-thoi-gian-nhan-ca")
    public ResponseEntity<?> orderByThoiGinaNhanCa() {
        try {
            GiaoCaResponse giaoCaResponse = _giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
            return ResponseEntity.ok(giaoCaResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("by-status-account")
    public ResponseEntity<?> findGiaoCaByTrangThaiAndIdAccount() {
        try {
            GiaoCaResponse giaoCaResponse = _giaoCaStaffService.findGiaoCaByTrangThaiAndIdAccount(TrangThaiGiaoCa.NHAN_CA, _commonSession.getUserId());
            return ResponseEntity.ok(new BaseResponse<Object>(HttpStatus.OK, giaoCaResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
