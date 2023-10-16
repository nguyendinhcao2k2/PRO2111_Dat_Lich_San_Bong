package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/giao-ca")
public class GiaoCaStaffController {
    @GetMapping
    public String viewKhoiTaoCa() {
        return "staff/nhan-ca";
    }
}
