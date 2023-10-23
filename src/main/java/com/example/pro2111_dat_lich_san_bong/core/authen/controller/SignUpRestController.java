package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import com.example.pro2111_dat_lich_san_bong.core.authen.dto.request.AccountRequest;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/authentication")
public class SignUpRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody AccountRequest accountRequest) {
        loginService.signUp(accountRequest);
        return new ResponseEntity<>("Check out Success", HttpStatus.OK);
    }
}
