package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.model.request.NuocUongRequest;

import java.util.List;

public interface NuocUongService {
    List<NuocUong> getAll();

    NuocUong findById(String id);

    NuocUong create(NuocUongRequest nuocUongRequest);

    void remove(String id);

    NuocUong update(String id, NuocUongRequest nuocUongRequest);
}
