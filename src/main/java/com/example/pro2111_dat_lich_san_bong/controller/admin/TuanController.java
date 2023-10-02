package com.example.pro2111_dat_lich_san_bong.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-view")
public class TuanController {
    @GetMapping("/1")
    public String viewHoaDon() {
        return "admin/ql-hoa-don-admin";
    }
    @GetMapping("/2")
    public String thanhToan() {
        return "admin/thanh-toan-admin";
    }
}
