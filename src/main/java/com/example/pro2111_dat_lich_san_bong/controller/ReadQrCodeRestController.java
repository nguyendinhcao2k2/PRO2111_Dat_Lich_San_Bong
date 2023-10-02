package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.service.PhieuDatLichService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camera")
@CrossOrigin("*")
public class ReadQrCodeRestController {

    @Autowired
    private PhieuDatLichService phieuDatLichService;


    @PostMapping("/check-qr-code")
    public ResponseEntity<HttpStatus> checkQrCode(@RequestBody String qrCode) {
        PhieuDatLich phieuDatLich = phieuDatLichService.findPhieuDatLichByMaQrCode(qrCode);
        if (phieuDatLich != null) {
            System.out.println(phieuDatLich.getMaQrCode());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
