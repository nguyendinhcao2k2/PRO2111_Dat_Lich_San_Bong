package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.model.request.NuocUongRequest;
import com.example.pro2111_dat_lich_san_bong.repository.NuocUongRepository;
import com.example.pro2111_dat_lich_san_bong.service.NuocUongService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NuocUongServiceImpl implements NuocUongService {

    @Autowired
    private NuocUongRepository nuocUongRepository;


    @Override
    public List<NuocUong> getAll() {
        return nuocUongRepository.findAll();
    }

    @Override
    public NuocUong findById(String id) {
        return nuocUongRepository.findById(id).get();
    }

    @Override
    @Transactional
    public NuocUong create(NuocUongRequest nuocUongRequest) {
        NuocUong nuocUong = new NuocUong(null,nuocUongRequest.getTenNuocUong(),nuocUongRequest.getSoLuong(), nuocUongRequest.getDonGia(),nuocUongRequest.getTrangThai());
        return nuocUongRepository.save(nuocUong);
    }

    @Override
    @Transactional
    public void remove(String id) {
        nuocUongRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NuocUong update(String id, NuocUongRequest nuocUongRequest) {
        NuocUong nuocUong = nuocUongRepository.findById(id).get();
        nuocUong.setTenNuocUong(nuocUongRequest.getTenNuocUong());
        nuocUong.setSoLuong(nuocUongRequest.getSoLuong());
        nuocUong.setDonGia(nuocUongRequest.getDonGia());
        nuocUong.setTrangThai(nuocUongRequest.getTrangThai());
        return nuocUongRepository.save(nuocUong);
    }
}
