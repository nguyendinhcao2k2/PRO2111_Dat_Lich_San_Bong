package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonDoiLichUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonDoiLichUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaDonDoiLichUserServiceImpl implements HoaDonDoiLichUserService {

    @Autowired
    private HoaDonDoiLichUserReponsitory hoaDonDoiLichUserReponsitory;

    @Override
    public HoaDon findById(String id) {
        try {
            return hoaDonDoiLichUserReponsitory.findById(id).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void update(HoaDon hoaDon) {
        try {
            hoaDonDoiLichUserReponsitory.saveAndFlush(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
