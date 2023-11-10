package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class Test {
    @GetMapping
    public String test() {
        return "owner/quan-ly-giao-ca";
    }
    @GetMapping("/dat-lich")
    public String TK() {
        return "utill/phieu-dat-lich";
    }

    @GetMapping("/thong-ke")
    public String thongKe() {
        return "owner/thong-ke";
    }

    @GetMapping("/manager")
    public String account(){
        return "owner/manager-admin";
    }
}
