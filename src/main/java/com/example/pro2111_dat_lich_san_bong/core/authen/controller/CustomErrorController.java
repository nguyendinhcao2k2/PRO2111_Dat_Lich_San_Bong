package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SessionConstant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @author caodinh
 */
@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (code != null) {
            Integer statusCode = Integer.valueOf(code.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/page-404";
            }
        }
        return "error/page-500";
    }

    @GetMapping("/login")
    public String loginFail() throws IOException {
        String obj = (String) session.getAttribute(SessionConstant.sessionRole);
        if (obj.equals(RoleConstant.roleUser)) {
            session.invalidate();
            return "authen/login-user";
        } else if (obj.equals(RoleConstant.roleAdmin)) {
            session.invalidate();
            return "authen/login-admin";
        }
        return "authen/home-login";
    }
}
