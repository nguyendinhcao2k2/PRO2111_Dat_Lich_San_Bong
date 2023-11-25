package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/staff/check-in")
public class CheckInStaffController {
    @GetMapping("")
    public String getViewStaffCheckIn(){
        return "staff/check-in-staff";
    }
}
