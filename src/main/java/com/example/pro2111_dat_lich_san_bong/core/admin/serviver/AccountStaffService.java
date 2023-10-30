package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import org.springframework.stereotype.Service;


public interface AccountStaffService {

    void createrAccountStaff(AccountStaffResquest accountStaffResquest);

    String deleteAccountStaff(String id);

}
