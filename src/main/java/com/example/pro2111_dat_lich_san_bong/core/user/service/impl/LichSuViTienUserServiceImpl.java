package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.LichSuViTienUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LichSuViTienUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuViTien;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichSuViTienUserServiceImpl implements LichSuViTienUserService {

    @Autowired
    private LichSuViTienUserReponsitory lichSuViTienUserReponsitory;

    @Override
    public LichSuViTien findAllByIdViTienCoc(String idViTienCoc) {
        try {
            return lichSuViTienUserReponsitory.findAllByIdViTienCoc(idViTienCoc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(LichSuViTien lichSuViTien) {
            try {
                lichSuViTienUserReponsitory.saveAndFlush(lichSuViTien);
            }catch (Exception e) {
                e.printStackTrace();
            }
    }
}
