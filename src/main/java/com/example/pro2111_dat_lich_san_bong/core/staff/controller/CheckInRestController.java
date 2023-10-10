package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.BaseResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.PhieuDatLichStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiCheckIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/camera")
public class CheckInRestController {

    @Autowired
    private PhieuDatLichStaffService phieuDatLichStaffService;

    private BaseResponse<Object> BaseResponseTemplate(HttpStatus httpStatus, String message, Object object) {
        return new BaseResponse<Object>(httpStatus, message, object);
    }

    @PostMapping("/check-qr-code")
    public ResponseEntity<?> readQrCode(@RequestBody String qrCode) {
        System.out.println(qrCode);
        PhieuDatLich phieuDatLich = phieuDatLichStaffService.findPhieuDatLichByMaQrCode(qrCode);
        TrangThaiCheckIn check = phieuDatLichStaffService.checkInSanBong(phieuDatLich);
        if (check == TrangThaiCheckIn.CHECK_IN_SUCCESS) {
            return ResponseEntity.ok(BaseResponseTemplate(HttpStatus.OK, "Successfully", phieuDatLich));
        } else if (check == TrangThaiCheckIn.CHECKED_IN) {
            return ResponseEntity.ok(BaseResponseTemplate(HttpStatus.ALREADY_REPORTED, "Has Been Checked In", null));
        } else if (check == TrangThaiCheckIn.CHECK_IN_INVALID) {
            return ResponseEntity.ok(BaseResponseTemplate(HttpStatus.NOT_FOUND, "QR Invalid", null));
        } else {
            return ResponseEntity.ok(BaseResponseTemplate(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE, "Server Error", null));
        }
    }
}
