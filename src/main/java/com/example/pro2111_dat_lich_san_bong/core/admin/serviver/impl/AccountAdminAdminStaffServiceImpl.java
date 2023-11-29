package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountStaffRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.AccountAdminStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ChucVuStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountAdminStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountAdminAdminStaffServiceImpl implements AccountAdminStaffService {

    @Autowired
    private AccountAdminStaffRepository accountAdminStaffRepository;

    @Autowired
    private ChucVuStaffRepository chucVuStaffRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ;

    @Override
    public Page<AccountStaffRespone> getAll(Pageable pageable) {
        try {
            Page<Account> listAccount = accountAdminStaffRepository.getAllByStaff(pageable);
            TypeToken<Page<AccountStaffRespone>> token = new TypeToken<Page<AccountStaffRespone>>() {
            };
            return modelMapper.map(listAccount, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }

    @Override
    public void createrAccountStaff(AccountStaffResquest accountStaffResquest) {
        ChucVu chucVu = chucVuStaffRepository.findByTenChucVu("ROLE_STAFF");
        Account account = modelMapper.map(accountStaffResquest, Account.class);
        account.setIdChucVu(chucVu.getId());
        account.setTrangThai(0);
        account.setMatKhau(bCryptPasswordEncoder().encode(accountStaffResquest.getMatKhau()));
        accountAdminStaffRepository.save(account);
    }

    @Override
    public String deleteAccountStaff(String id) {
        try {
            if (accountAdminStaffRepository.existsById(id)) {
                accountAdminStaffRepository.deleteById(id);
                return "Xóa thành công";
            }
            return "Xóa thất bại";

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "Xóa thất bại";
    }

    @Override
    public Account findFirstByEmailAAndTaiKhoan(String taiKhoan) {
        try {
            return accountAdminStaffRepository.findFirstByEmailAAndTaiKhoan(taiKhoan);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<AccountStaffRespone> findAccountBySoDienThoai(String soDienThoai,Pageable pageable) {
        try {
            Page<Account> listAccount = accountAdminStaffRepository.findAccountBySoDienThoai(soDienThoai,pageable);
            TypeToken<Page<AccountStaffRespone>> token = new TypeToken<Page<AccountStaffRespone>>() {
            };
            return modelMapper.map(listAccount, token.getType());
        } catch (
                Exception e
        ) {
            return null;
        }
    }
}
