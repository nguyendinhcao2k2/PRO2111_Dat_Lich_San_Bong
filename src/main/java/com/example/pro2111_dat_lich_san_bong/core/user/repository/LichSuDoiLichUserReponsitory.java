package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.entity.LichSuDoiLich;
import com.example.pro2111_dat_lich_san_bong.repository.LichSuDoiLichReponsitory;
import org.springframework.stereotype.Repository;

@Repository
public interface LichSuDoiLichUserReponsitory extends LichSuDoiLichReponsitory {

    LichSuDoiLich findAllByIdNguoiDungAndTrangThai(String idNguoiDung, Integer trangThai);
}
