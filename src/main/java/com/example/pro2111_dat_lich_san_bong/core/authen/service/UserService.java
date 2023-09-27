package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import com.example.pro2111_dat_lich_san_bong.infrastructure.config.security.CustomOAuth2User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author caodinh
 */
public interface UserService {

    void processOAuthPostLogin(HttpServletResponse response) throws IOException;


}
