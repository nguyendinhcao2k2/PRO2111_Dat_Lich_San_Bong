package com.example.pro2111_dat_lich_san_bong.core.utils;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.IHoaDonSanCaStaffQRCodeService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("api/v1/staff")
public class RestControllerScanQR {

    @Autowired
    private IHoaDonSanCaStaffQRCodeService hoaDonSanCaStaffQRCodeService;

    @PostMapping("/camera/check-qr-code")
    public ResponseEntity<?> checkQRCode(@RequestBody String qrCode) {
        try {
            HoaDonSanCa hoaDonSanCa = hoaDonSanCaStaffQRCodeService.findFirstByMaQR(qrCode);
            if (hoaDonSanCa == null) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "Không tìm thấy qr code"));
            }
            // trang thai =0 la chưa check in
            // trang thai =1 la đã check in
            if (hoaDonSanCa.getTrangThai() == 1) {
                return ResponseEntity.ok(new BaseResponse<>(HttpStatus.ALREADY_REPORTED, "Đã được check-in"));
            }
            hoaDonSanCa.setTrangThai(1);
//            hoaDonSanCa.setThoiGianCheckIn(new Time(new Date().getTime()));
            hoaDonSanCaStaffQRCodeService.updateHoaDonSanCaStaffQRCode(hoaDonSanCa);
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.OK, "Check-in thành công!"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
    }
}
