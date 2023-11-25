package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/api/v1/staff")
public class Dynamic_dat_lich {

    @GetMapping("/dat-lich-dynamic")
    public String datLich(Model model){

        return "staff/dynamic_content";
    }
}
