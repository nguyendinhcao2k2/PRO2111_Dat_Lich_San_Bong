package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("api/v1/staff/giao-ca/display")
public class GiaoCaStaffController {

    @GetMapping()
    public String giaoCa() {
        return "staff/giao-ca";
    }

    @GetMapping("/nhan-ca")
    public String viewKhoiTaoCa() {
        return "staff/nhan-ca";
    }
}
