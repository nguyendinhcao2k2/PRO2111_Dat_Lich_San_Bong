package com.example.pro2111_dat_lich_san_bong.core.staff.view_controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/staff/giao-ca/view")
public class ViewGiaoCaRestController {

    @GetMapping()
    public String giaoCa() {
        return "/api/v1/staff/giao-ca/display";
    }

    @GetMapping("/nhan-ca")
    public String nhanCa() {
        return "/api/v1/staff/nhan-ca/display/nhan-ca";
    }
}
