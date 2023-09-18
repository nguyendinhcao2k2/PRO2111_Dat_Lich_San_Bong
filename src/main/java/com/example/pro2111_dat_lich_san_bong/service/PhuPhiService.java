package com.example.pro2111_dat_lich_san_bong.service;


import com.example.pro2111_dat_lich_san_bong.entity.PhuPhi;
import com.example.pro2111_dat_lich_san_bong.model.request.PhuPhiRequest;

import java.util.List;
import java.util.Optional;

public interface PhuPhiService {
    List<PhuPhi> finAll();

    Optional<PhuPhi> findById(String id);

    void delete(String id);

    PhuPhi update(String id, PhuPhiRequest phuPhiRequest);

    PhuPhi create(PhuPhiRequest phuPhiRequest);

    String genCodeAuto();
}
