package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.model.request.DoThueRequest;

import java.util.List;

public interface DoThueService {
    List<DoThue> getAll();

    DoThue findById(String id);

    DoThue create(DoThueRequest doThueRequest);

    void remove(String id);

    DoThue update(String id, DoThueRequest  doThueRequest);
}
