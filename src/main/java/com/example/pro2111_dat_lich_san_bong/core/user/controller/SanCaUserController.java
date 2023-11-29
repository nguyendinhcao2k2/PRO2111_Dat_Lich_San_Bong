package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/api/v1/san-ca")
public class SanCaUserController extends BaseController {
    @GetMapping("/trang-chu")
    public String demo(){
        return "/user/trang-chu";
    }
}
