package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import com.example.pro2111_dat_lich_san_bong.core.authen.repo.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceForgotPassImpl implements AccountServiceForgotPass {

    @Autowired
    private AccountRepository accountRepositoryAuthen;

    @Override
    public Account getAccountByUsername(String email, String taiKhoan) {
        try {
            return accountRepositoryAuthen.getAccountByEmailAndTaiKhoan(email, taiKhoan);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            accountRepositoryAuthen.saveAndFlush(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
