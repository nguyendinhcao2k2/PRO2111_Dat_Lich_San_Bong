package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class TrangCaNhanController {

    @GetMapping("")
    public String viewPageProfile(){
        return "user/trang-ca-nhan-user";
    }
}
