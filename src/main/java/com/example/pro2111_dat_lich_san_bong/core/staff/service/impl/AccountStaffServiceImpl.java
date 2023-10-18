package com.example.pro2111_dat_lich_san_bong.core.staff.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.AccountStaffReponsitoty;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountStaffServiceImpl implements IAccountStaffService {

    @Autowired
    private AccountStaffReponsitoty _accountStaffReponsitoty;

    @Autowired
    private ModelMapper _modelMapper;

    @Override
    public AccountResponse findAccountById(String id) {
        try {
            Account account = _accountStaffReponsitoty.findAccountById(id);
            if (account != null) {
                return _modelMapper.map(account, AccountResponse.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AccountResponse> getAccountByChucVu(String role) {
        try {
            List<Account> accountsList = _accountStaffReponsitoty.getAccountByChucVu(role);
            TypeToken<List<AccountResponse>> typeToken = new TypeToken<List<AccountResponse>>() {
            };
            List<AccountResponse> accountResponseList = _modelMapper.map(accountsList, typeToken.getType());
            return accountResponseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
