package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;

public interface HoaDonDoiLichUserService {
    HoaDon findById(String id);

    void update(HoaDon hoaDon);
}
