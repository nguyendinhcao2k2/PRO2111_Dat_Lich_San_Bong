package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();

    Account getOneAccount(String id);

    Boolean createNewAccount(Account account);

    Boolean updateAccount(String id, Account account);

    Boolean deleteAccount(String id);
}
