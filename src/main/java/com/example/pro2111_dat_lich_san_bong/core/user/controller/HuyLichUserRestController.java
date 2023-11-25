package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/huy-lich-dat-san")
public class HuyLichUserRestController {

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @DeleteMapping("/{idSanCa}")
    public ResponseEntity<?> huyLichSanBongUser(@PathVariable("idSanCa") String idSanCa) {
        try {
            if (sanCaUserService.findSanCaById(idSanCa) != null) {
                HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCa);
                hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCa.getId());
                sanCaUserService.deleteSanCaById(idSanCa);
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Successfully"));
            }
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Not Found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Not Found"));
        }
    }

}
