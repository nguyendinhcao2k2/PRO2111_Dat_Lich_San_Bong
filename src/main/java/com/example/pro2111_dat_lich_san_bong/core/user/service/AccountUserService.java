package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.ProfileRequest;
import com.example.pro2111_dat_lich_san_bong.entity.Account;

public interface AccountUserService {
    Account findById(String idAccount);

    void saveAccount(Account account);

    void saveAccountOdt(ProfileRequest profileRequest);
}
