package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
@RequestMapping("/authentication")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "authen/login";
    }

    @GetMapping("/403")
    public String process403() {
        return "authen/403";
    }

}
