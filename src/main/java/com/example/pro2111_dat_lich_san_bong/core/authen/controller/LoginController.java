package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import com.example.pro2111_dat_lich_san_bong.core.authen.dto.request.AccountRequest;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.AccountService;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.LoginService;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SessionConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
@RequestMapping("/authentication")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginService loginService;

    @GetMapping("/home-login")
    public String homeLogin() {
        return "authen/home-login";
    }

    @GetMapping("/user-login")
    public String userLogin() {
        session.setAttribute(SessionConstant.sessionRole, RoleConstant.roleUser);
        return "authen/login-user";
    }

    @GetMapping("/staff-login")
    public String staffLogin() {
        session.setAttribute(SessionConstant.sessionRole, RoleConstant.roleStaff);
        return "authen/login-staff";
    }

    @GetMapping("/admin-login")
    public String adminLogin() {
        session.setAttribute(SessionConstant.sessionRole, RoleConstant.roleAdmin);
        return "authen/login-admin";
    }

    @GetMapping("/403")
    public String pag403() {
        return "error/page-403";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("account") AccountRequest accountRequest) {
        boolean check = loginService.signUp(accountRequest);
        if (check == true) {
            return "authen/home-login";
        } else {
            return "authen/login-user";
        }
    }

}
