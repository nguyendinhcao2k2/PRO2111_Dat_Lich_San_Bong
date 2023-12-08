package com.example.pro2111_dat_lich_san_bong.core.authen.service;


import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.data.repository.query.Param;

public interface AccountServiceForgotPass {
    Account getAccountByUsername(String email,String taiKhoan);

    void updateAccount(Account account);
}
