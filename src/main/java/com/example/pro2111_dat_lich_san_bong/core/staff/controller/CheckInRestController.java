package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/camera")
public class CheckInRestController {
    @PostMapping("/check-qr-code")
    public ResponseEntity<?> readQrCode(@RequestBody String qrCode) {
        System.out.println(qrCode);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
