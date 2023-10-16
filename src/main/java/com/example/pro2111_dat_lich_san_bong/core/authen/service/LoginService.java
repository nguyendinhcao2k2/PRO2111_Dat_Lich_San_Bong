package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import com.example.pro2111_dat_lich_san_bong.core.authen.dto.request.AccountRequest;
import com.example.pro2111_dat_lich_san_bong.core.authen.repo.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author caodinh
 */
@Service
public class LoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean signUp(AccountRequest accountRequest) {
        if (accountRequest.getAccount().isBlank()) {
            return false;
        }
        Account account1 = accountRepository.findByUsername(accountRequest.getAccount());
        if (account1 != null) {
            return false;
        }
        if (accountRequest.getDisplayName().isBlank()) {
            return false;
        }
        String regex = "0\\d{9}";
        if (accountRequest.getPhoneNumber().isBlank()) {
            return false;
        } else if (!accountRequest.getPhoneNumber().matches(regex)) {
            return false;
        }
        if (accountRequest.getPassword().isBlank()) {
            return false;
        }
        if (accountRequest.getRePassword().isBlank()) {
            return false;
        }
        if (!accountRequest.getPassword().equals(accountRequest.getRePassword())) {
            return false;
        }

        Account account = new Account();
        account.setDisplayName(accountRequest.getDisplayName());
        account.setIdChucVu(chucVuRepository.findByTenChucVu(RoleConstant.roleUser).getId());
        account.setMatKhau(passwordEncoder.encode(accountRequest.getPassword()));
        account.setSoDienThoai(accountRequest.getPhoneNumber());
        account.setTaiKhoan(accountRequest.getAccount());
        account.setTrangThai(0);
        accountRepository.save(account);
        return true;
    }

}
