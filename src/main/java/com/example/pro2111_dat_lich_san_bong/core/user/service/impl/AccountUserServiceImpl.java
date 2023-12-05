package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.ProfileRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.AccountUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.AccountUserService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Account findById(String idAccount) {
        try {
            return accountUserRepository.findById(idAccount).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void saveAccount(Account account) {
        try {
            accountUserRepository.saveAndFlush(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAccountOdt(ProfileRequest profileRequest) {
        try {
            if (profileRequest != null) {
                Account account = mapper.map(profileRequest, Account.class);
                accountUserRepository.saveAndFlush(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
