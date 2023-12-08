package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.LichSuSanBongAdminService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLichSuSanBong;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/staff/huy-lich-dat-san")
public class HuyLichStaffRestController {

    @Autowired
    private HoaDonSanCaUserService hoaDonSanCaUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private LichSuSanBongAdminService lichSuSanBongAdminService;

    @DeleteMapping("/{idSanCa}")
    public ResponseEntity<?> huyLichSanBongUser(@PathVariable("idSanCa") String idSanCa) {
        try {
            if (sanCaUserService.findSanCaById(idSanCa) != null) {
                HoaDonSanCa hoaDonSanCa = hoaDonSanCaUserService.findByIdSanCa(idSanCa);
                hoaDonSanCaUserService.deleteByIdHoaDonSanCa(hoaDonSanCa.getId());
                sanCaUserService.deleteSanCaById(idSanCa);
                //create Lich su san bong

                LichSuSanBong lichSuSanBong = new LichSuSanBong();
                lichSuSanBong.setTrangThai(TrangThaiLichSuSanBong.HUY_LICH.ordinal());
                lichSuSanBong.setNgayThucHien(LocalDateTime.now());
                lichSuSanBongAdminService.createOrUpdate(lichSuSanBong);

                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Successfully"));
            }
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Not Found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Not Found"));
        }
    }

}
