package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.LoaiSanUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LoaiSanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class LoaiSanUserServiceImpl implements LoaiSanUserService {

    @Autowired
    private LoaiSanUserRepository loaiSanUserRepository;

    @Override
    public List getAllLoaiSan() {
        return loaiSanUserRepository.getAllLoaiSan();
    }
}
