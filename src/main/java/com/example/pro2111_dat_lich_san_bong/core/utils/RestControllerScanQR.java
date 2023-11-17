package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/staff")
public class RestControllerScanQR {
    @PostMapping("/camera/check-qr-code")
    public void checkQRCode(@RequestBody String qrCode){
        System.out.println(qrCode);
    }
}
