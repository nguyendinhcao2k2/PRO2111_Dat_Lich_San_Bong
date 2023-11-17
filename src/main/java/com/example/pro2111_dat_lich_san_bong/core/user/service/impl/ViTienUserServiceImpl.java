package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.ViTienUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ViTienUserService;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thepvph20110
 */
@Service
public class ViTienUserServiceImpl implements ViTienUserService {

    @Autowired
    private ViTienUserRepository viTienUserRepository;


    @Override
    public ViTienCoc saveViTen(ViTienCoc viTienCoc) {
        return viTienUserRepository.save(viTienCoc);
    }
}
