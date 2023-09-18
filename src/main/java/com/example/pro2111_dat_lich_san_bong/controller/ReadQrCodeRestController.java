package com.example.pro2111_dat_lich_san_bong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camera")
public class ReadQrCodeRestController {
    @PostMapping("/check-qr-code")
    public ResponseEntity<HttpStatus> checkQrCode(@RequestBody String qrCode) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
