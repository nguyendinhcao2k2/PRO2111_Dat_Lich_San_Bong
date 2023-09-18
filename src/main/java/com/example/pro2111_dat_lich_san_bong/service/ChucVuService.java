package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.model.request.ChucVuRequest;

import java.util.List;
import java.util.Optional;

public interface ChucVuService {
    List<ChucVu> finAll();

    Optional<ChucVu> findById(String id);

    void delete(String id);

    ChucVu update(String id, ChucVuRequest chucVuRequest);

    ChucVu create(ChucVuRequest chucVuRequest);
}
