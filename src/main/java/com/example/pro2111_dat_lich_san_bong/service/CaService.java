package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.model.request.CaRequest;

import java.util.List;

public interface CaService {

    List<Ca> getAll();

    Ca findById(String id);

    Ca create(CaRequest caRequest);

    void remove(String id);

    Ca update(String id, CaRequest  caRequest);
}
