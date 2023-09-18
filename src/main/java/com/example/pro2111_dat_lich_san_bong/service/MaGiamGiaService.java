package com.example.pro2111_dat_lich_san_bong.service;


import com.example.pro2111_dat_lich_san_bong.entity.MaGiamGia;
import com.example.pro2111_dat_lich_san_bong.model.request.MaGiamGiaRequest;

import java.util.List;
import java.util.Optional;

public interface MaGiamGiaService {
    List<MaGiamGia> finAll();

    Optional<MaGiamGia> findById(String id);

    void delete(String id);

    MaGiamGia update(String id, MaGiamGiaRequest maGiamGiaRequest);

    MaGiamGia create(MaGiamGiaRequest maGiamGiaRequest);
}
