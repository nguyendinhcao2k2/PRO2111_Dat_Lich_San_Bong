package com.example.pro2111_dat_lich_san_bong.core.admin.serviver.impl;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.response.ThongTinAccountThongKeReponse;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.AccountAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountThongKeServiceImpl implements AccountThongKeService {

    @Autowired
    private AccountAdminRepository accountAdminRepository;

    @Override
    public List<ThongTinAccountThongKeReponse> getAllAccountByChucVu(String tenChucVu) {
        try {
            List<ThongTinAccountThongKeReponse> result = accountAdminRepository.getAllAccountByChucVu(tenChucVu);
            if (result != null) {
                return result;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
