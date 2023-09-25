package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.ViTien;

import java.util.List;

public interface ViTienService {
    List<ViTien> getAllViTiens();

    ViTien getOneViTien(String id);

    Boolean createNewViTien(ViTien viTien);

    Boolean updateViTien(String id, ViTien viTien);

    Boolean deleteViTien(String id);
}
