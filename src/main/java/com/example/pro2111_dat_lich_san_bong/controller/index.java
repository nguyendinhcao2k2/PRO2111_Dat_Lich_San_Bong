package com.example.pro2111_dat_lich_san_bong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/index")
public class index {

    @GetMapping("/demo")
    public String index(){
        return "user/book-soccer-field";
    }
}
