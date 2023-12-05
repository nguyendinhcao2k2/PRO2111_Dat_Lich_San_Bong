package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/static")
public class ForgotPassController {

    @GetMapping("/forgot-pass")
    public String getForgetPassword() {
        return "authen/reset-password";
    }

    @GetMapping("/otp-form")
    public String getOtpForm() {
        return "authen/otp-form";
    }
}
