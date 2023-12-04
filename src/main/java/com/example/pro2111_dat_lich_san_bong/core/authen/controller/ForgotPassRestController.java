package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import com.example.pro2111_dat_lich_san_bong.core.authen.service.AccountServiceForgotPass;
import com.example.pro2111_dat_lich_san_bong.core.utils.SenMailForgotPass;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/static")
public class ForgotPassRestController {

    @Autowired
    private AccountServiceForgotPass accountServiceForgotPass;

    @Autowired
    private SenMailForgotPass senMailForgotPass;

    @GetMapping("/check-email")
    public BaseResponse<?> checkByEmail(@RequestParam("email") String email, @RequestParam("taiKhoan") String taiKhoan) {
        try {
            Account account = accountServiceForgotPass.getAccountByUsername(email, taiKhoan);
            if (account != null) {
                Integer random = (int) Math.floor((Math.random() * 899999) + 100000);
                senMailForgotPass.sendMailForGotPass("Code xác thực mail: " + random, account.getEmail(), "Code xác thực mail");
                account.setIdHangKhachHang(String.valueOf(random));
                accountServiceForgotPass.updateAccount(account);
                return new BaseResponse<>(HttpStatus.OK, account);
            }
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid email address");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid email address");
        }
    }

    @PostMapping("/send-mail-forgot-pass")
    public BaseResponse<?> sendMail(@RequestBody Account account) {
        try {
            Integer passNew = (int) Math.floor((Math.random() * 899999) + 100000);
            senMailForgotPass.sendMailForGotPass("Mật khẩu mới của bạn là: " + passNew, account.getEmail(), "CẤP LẠI MẬT KHẨU MỚI");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            account.setMatKhau(passwordEncoder.encode(String.valueOf(passNew)));
            accountServiceForgotPass.updateAccount(account);
            return new BaseResponse<>(HttpStatus.OK, "Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Error send mail");
        }
    }

}
