package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.user.service.AccountUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/profile")
public class TrangCaNhanController {

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private AccountUserService accountUserService;

    @GetMapping("")
    public String profileCaNhan(Model model) {
        try {
            Account account = accountUserService.findById(commonSession.getUserId());
            if (account == null) {
                return "redirect:/authentication/home-login";
            }
            model.addAttribute("account", account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/trang-ca-nhan-user";
    }
}
