package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.user.controller.rest.PassChangRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.ProfileRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.AccountUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileRestController {

    @Autowired
    private CommonSession commonSession;

    @Autowired
    private AccountUserService accountUserService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("check-password")
    public BaseResponse<?> checkPasswordCurrency(@RequestBody PassChangRequest passChangRequest) {
        try {
            Account account = accountUserService.findById(commonSession.getUserId());
            if (account == null) {
                return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
            }
            boolean checkPass = passwordEncoder.matches(passChangRequest.getPassCurrency(), account.getMatKhau());
            if (checkPass) {
                account.setMatKhau(passwordEncoder.encode(passChangRequest.getPassNew()));
                accountUserService.saveAccount(account);
                return new BaseResponse<>(HttpStatus.OK, "Successfully");
            }
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        }
    }

    @GetMapping("/thong-tin")
    public BaseResponse<?> getThongTin() {
        try {
            Account account = accountUserService.findById(commonSession.getUserId());
            if (account == null) {
                return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
            }
            return new BaseResponse<>(HttpStatus.OK, account);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        }
    }

    ;

    @PutMapping("/update")
    public BaseResponse<?> updateThongTin(@RequestBody ProfileRequest profileRequest) {
        try {
            accountUserService.saveAccountOdt(profileRequest);
            return new BaseResponse<>(HttpStatus.OK, profileRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error");
        }
    }

}
