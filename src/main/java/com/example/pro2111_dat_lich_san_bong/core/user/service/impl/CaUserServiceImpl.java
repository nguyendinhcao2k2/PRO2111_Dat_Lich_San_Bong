package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.CaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class CaUserServiceImpl implements CaUserService {

    @Autowired
    private CaUserRepository caUserRepository;


    @Override
    public List getAllCaByIdLoaiSan(String idLoaiSan) {
        return caUserRepository.getAllCaByIdLoaiSan(idLoaiSan);
    }
}
