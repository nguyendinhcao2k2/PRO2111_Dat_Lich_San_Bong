package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.AccountStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.ChucVuStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountCreateStaffServiceImpl implements AccountStaffService {

    @Autowired
    private AccountStaffRepository accountStaffRepository;

    @Autowired
    private ChucVuStaffRepository chucVuStaffRepository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Override
    public void createrAccountStaff(AccountStaffResquest accountStaffResquest) {
        ChucVu chucVu = chucVuStaffRepository.findByTenChucVu("ROLE_STAFF");
        Account account = modelMapper.map(accountStaffResquest, Account.class);
        account.setIdChucVu(chucVu.getId());
        account.setTrangThai(0);
        account.setMatKhau(bCryptPasswordEncoder().encode(accountStaffResquest.getMatKhau()));
        accountStaffRepository.save(account);
    }

    @Override
    public String deleteAccountStaff(String id) {
        try {
            if(accountStaffRepository.existsById(id)){
                accountStaffRepository.deleteById(id);
                return "Xóa thành công";
            }
            return "Xóa thất bại";

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return "Xóa thất bại";
    }
}
