package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reset-pass")
@Controller
public class ResetPassController {
    @GetMapping("")
    public String returnView(){
        return "authen/reset-password";
    }
}
