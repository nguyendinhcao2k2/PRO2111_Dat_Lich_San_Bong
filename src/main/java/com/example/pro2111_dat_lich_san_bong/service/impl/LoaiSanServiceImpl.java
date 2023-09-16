package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.model.request.LoaiSanRequest;
import com.example.pro2111_dat_lich_san_bong.repository.LoaiSanRepository;
import com.example.pro2111_dat_lich_san_bong.service.LoaiSanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSanServiceImpl implements LoaiSanService {

    @Autowired
    private LoaiSanRepository loaiSanRepository;

    @Override
    public List<LoaiSan> getAll() {
        return loaiSanRepository.findAll();
    }

    @Override
    public LoaiSan findById(String id) {
        return loaiSanRepository.findById(id).get();
    }

    @Override
    public LoaiSan create(LoaiSanRequest loaiSanRequest) {
        LoaiSan loaiSan = new LoaiSan(null,loaiSanRequest.getTenLoaiSan(), loaiSanRequest.getMoTa());
        return loaiSanRepository.save(loaiSan);
    }

    @Override
    public void remove(String id) {
        loaiSanRepository.deleteById(id);
    }

    @Override
    public LoaiSan update(String id, LoaiSanRequest loaiSanRequest) {
        LoaiSan loaiSan = loaiSanRepository.findById(id).get();
        loaiSan.setTenLoaiSan(loaiSanRequest.getTenLoaiSan());
        loaiSan.setMoTa(loaiSanRequest.getMoTa());
        return loaiSanRepository.save(loaiSan);
    }
}
