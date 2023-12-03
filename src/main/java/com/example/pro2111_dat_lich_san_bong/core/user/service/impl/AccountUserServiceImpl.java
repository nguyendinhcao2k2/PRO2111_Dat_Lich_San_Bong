package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.AccountUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.AccountUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Override
    public Account findById(String idAccount) {
        try {
            return accountUserRepository.findById(idAccount).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
