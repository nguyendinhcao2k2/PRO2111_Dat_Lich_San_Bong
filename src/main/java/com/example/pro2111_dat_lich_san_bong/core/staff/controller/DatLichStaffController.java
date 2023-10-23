package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
@RequestMapping("/api/v1/staff")
public class DatLichStaffController {

    @GetMapping("/view-dat-lich")
    public String viewDatLich(){
        return "staff/nhan-vien-dat-lich";
    }
}
