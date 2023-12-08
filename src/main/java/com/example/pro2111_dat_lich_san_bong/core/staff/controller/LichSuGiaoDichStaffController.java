package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LichSuGiaoDichStaffController {

    @GetMapping("staff/lich-su-giao-dich")
    public String getDisplayLichSuGiaoDich(){
        return "staff/lich-su-hoa-don-staff";
    }
}
