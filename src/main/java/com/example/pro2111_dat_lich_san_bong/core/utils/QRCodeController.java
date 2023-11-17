package com.example.pro2111_dat_lich_san_bong.core.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/staff")
@Controller
public class QRCodeController {

    @GetMapping("/qr-code")
    public String getQRCode(){
        return "staff/quet-qr";
    }
}
