package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.ThanhToan;


import java.util.List;

public interface ThanhToanService {
    List<ThanhToan> getAllThanhToan();

    ThanhToan getOneThanhToan(String id);

    Boolean createNewThanhToan(ThanhToan thanhToan);

    Boolean updateThanhToan(String id, ThanhToan thanhToan);

    Boolean deleteThanhToan(String id);

}
