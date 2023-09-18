package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.BienDongVi;
import com.example.pro2111_dat_lich_san_bong.model.request.BienDongViRequest;

import java.util.List;
import java.util.Optional;

public interface BienDongViService {
    List<BienDongVi> finAll();

    Optional<BienDongVi> findById(String id);

    void delete(String id);

    BienDongVi update(String id, BienDongViRequest bienDongViRequest);

    BienDongVi create(BienDongViRequest bienDongViRequest);
}
