package com.example.pro2111_dat_lich_san_bong;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/cho")
    public String get(){
        return "owner/quan-ly-ca.html";
    }
}