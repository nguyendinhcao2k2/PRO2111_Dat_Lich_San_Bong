package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuDoiLich;

public interface LichSuDoiLichUserService {

    void save(LichSuDoiLich lichSuDoiLich);

    void deleteById(String id);

    LichSuDoiLich findAllByIdNguoiDungAndTrangThai(String idNguoiDung, Integer trangThai);
}
