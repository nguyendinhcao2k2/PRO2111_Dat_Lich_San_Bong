package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.AccountAdminResponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.AccountAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class AccountAdminServiceImpl implements AccountAdminService {

    @Autowired
    private AccountAdminRepository accountAdminRepository ;


    @Override
    public List<AccountAdminResponse> getList() {
        return accountAdminRepository.getALl();
    }
}
