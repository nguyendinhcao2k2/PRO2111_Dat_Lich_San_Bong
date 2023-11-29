package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author caodinh
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void processOAuthPostLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/v1/user/trang-chu/danh-sach-lich-dat");
    }
}

