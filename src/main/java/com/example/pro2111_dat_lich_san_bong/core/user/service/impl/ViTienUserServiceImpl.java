package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.ViTienUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.ViTienUserService;
import com.example.pro2111_dat_lich_san_bong.entity.ViTienCoc;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public ViTienCoc updateViTien(ViTienCoc viTienCoc) {
        try {
            return viTienUserRepository.saveAndFlush(viTienCoc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ViTienCoc findByIdHoaDon(String idHoaDon) {
        try {
            return viTienUserRepository.findAllByIdHoaDon(idHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ViTienCoc findById(String id) {
        try {
            return viTienUserRepository.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
