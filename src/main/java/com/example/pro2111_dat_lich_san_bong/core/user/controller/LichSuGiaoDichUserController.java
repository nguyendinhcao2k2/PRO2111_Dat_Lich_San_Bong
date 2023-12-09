package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LichSuGiaoDichUserController {

    @GetMapping("user/lich-su-giao-dich")
    public String getDisplayLichSuGiaoDich(){
        return "user/lich-su-hoa-don-user";
    }
}
