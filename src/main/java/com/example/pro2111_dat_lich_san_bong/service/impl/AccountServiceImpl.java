package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getOneAccount(String id) {
        if (accountRepository.existsById(id)) {
            return accountRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewAccount(Account account) {
        if (!accountRepository.existsById(account.getId())) {
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateAccount(String id, Account account) {
        if (accountRepository.existsById(id)) {
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAccount(String id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
