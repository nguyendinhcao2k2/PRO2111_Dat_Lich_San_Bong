package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caodinh
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (code != null) {
            Integer statusCode = Integer.valueOf(code.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/page-404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/page-403";
            }
        }
        return "error/page-500";
    }

}
