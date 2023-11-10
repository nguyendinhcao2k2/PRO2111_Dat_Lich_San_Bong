package com.example.pro2111_dat_lich_san_bong.core.admin.serviver;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.AccountStaffResquest;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountStaffRespone;
import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.SanBongAdminRespone;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountAdminStaffService {

    Page<AccountStaffRespone> getAll(Pageable pageable);

    void createrAccountStaff(AccountStaffResquest accountStaffResquest);

    String deleteAccountStaff(String id);

    Account findFirstByEmailAAndTaiKhoan(String taiKhoan);

    Page<AccountStaffRespone> findAccountBySoDienThoai(String soDienThoai,Pageable pageable);

}
