package com.example.pro2111_dat_lich_san_bong.core.staff.service;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAccountStaffService {

    AccountResponse findAccountById(String id);

    List<AccountResponse> getAccountByChucVu( String role);
}
