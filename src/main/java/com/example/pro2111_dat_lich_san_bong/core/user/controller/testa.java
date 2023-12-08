package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user/test")
public class testa {
    @GetMapping("")
    public  String getA(){
        return "user/lich-su-hoa-don-user";
    }
}
