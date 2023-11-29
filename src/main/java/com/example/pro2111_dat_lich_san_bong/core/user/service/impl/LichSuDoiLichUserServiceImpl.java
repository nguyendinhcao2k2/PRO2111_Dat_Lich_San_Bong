package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.LichSuDoiLichUserReponsitory;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LichSuDoiLichUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LichSuDoiLich;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuDoiLichUserServiceImpl implements LichSuDoiLichUserService {

    @Autowired
    private LichSuDoiLichUserReponsitory lichSuDoiLichUserReponsitory;

    @Override
    @Transactional
    public void save(LichSuDoiLich lichSuDoiLich) {
        try {
            lichSuDoiLichUserReponsitory.saveAndFlush(lichSuDoiLich);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        try {
            lichSuDoiLichUserReponsitory.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LichSuDoiLich findAllByIdNguoiDungAndTrangThai(String idNguoiDung, Integer trangThai) {
        try {
            return lichSuDoiLichUserReponsitory.findAllByIdNguoiDungAndTrangThai(idNguoiDung, trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void saveAll(List list) {
        try {
            lichSuDoiLichUserReponsitory.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LichSuDoiLich> findAllByIdNguoiDungAndTrangThaiList(String idNguoiDung, Integer trangThai) {
        try {
           return lichSuDoiLichUserReponsitory.findByIdNguoiDungAndTrangThai(idNguoiDung, trangThai);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
