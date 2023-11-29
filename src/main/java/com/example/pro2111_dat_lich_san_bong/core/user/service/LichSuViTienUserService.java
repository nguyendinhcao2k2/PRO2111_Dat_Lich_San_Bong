package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuViTien;

public interface LichSuViTienUserService {

    LichSuViTien findAllByIdViTienCoc(String idViTienCoc);

    void saveOrUpdate(LichSuViTien lichSuViTien);
}
