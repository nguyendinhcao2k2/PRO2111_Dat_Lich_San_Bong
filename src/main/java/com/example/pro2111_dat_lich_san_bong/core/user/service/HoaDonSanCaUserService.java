package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface HoaDonSanCaUserService {

    void saveAllHoaDonSanCa(List list);

    void saveHoaDonSanCa(HoaDonSanCa hoaDonSanCa);

    List findAllByIdHoaDon(String idHoaDon);

    void deleteAllByIdHoaDon(String idHoaDon);

    HoaDonSanCa findById(String id);

    void  deleteByIdHoaDonSanCa(String id);

    HoaDonSanCa findByIdSanCa(String id);
}
