package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {
    @GetMapping("/api/v1/trang-chu")
    public String get(){
        return "user/trang-chu";
    }
}
