package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.request.CaRequest;
import com.example.pro2111_dat_lich_san_bong.model.request.LoaiSanRequest;

import java.util.List;

public interface LoaiSanService{
    List<LoaiSan> getAll();

    LoaiSan findById(String id);

    LoaiSan create(LoaiSanRequest loaiSanRequest);

    void remove(String id);

    LoaiSan update(String id, LoaiSanRequest  loaiSanRequest);
}
