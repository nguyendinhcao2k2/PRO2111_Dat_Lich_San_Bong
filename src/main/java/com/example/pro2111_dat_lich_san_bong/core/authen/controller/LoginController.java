package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SessionConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
@RequestMapping("/authentication")
public class LoginController {

    @Autowired
    private HttpSession session;

    @GetMapping("/home-login")
    public String homeLogin() {
        return "authen/home-login";
    }

    @GetMapping("/user-login")
    public String userLogin() {
        session.setAttribute(SessionConstant.sessionRole, RoleConstant.roleUser);
        return "authen/login-user";
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

}
