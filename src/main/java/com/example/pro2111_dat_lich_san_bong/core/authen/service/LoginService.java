package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import com.example.pro2111_dat_lich_san_bong.core.authen.dto.request.AccountRequest;
import com.example.pro2111_dat_lich_san_bong.core.authen.repo.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.exception.RestApiException;
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


    public void signUp(AccountRequest accountRequest) {
        Account account1 = accountRepository.findByUsername(accountRequest.getAccount());
        if (account1 != null) {
          throw new RestApiException("Tên Tài Khoản đã tồn tại");
        }
        Account account = new Account();
        account.setDisplayName(accountRequest.getDisplayName());
        account.setIdChucVu(chucVuRepository.findByTenChucVu(RoleConstant.roleUser).getId());
        account.setMatKhau(passwordEncoder.encode(accountRequest.getPassword()));
        account.setSoDienThoai(accountRequest.getPhoneNumber());
        account.setTaiKhoan(accountRequest.getAccount());
        account.setTrangThai(0);
        accountRepository.save(account);
    }

}
