package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import com.example.pro2111_dat_lich_san_bong.model.request.CaRequest;
import com.example.pro2111_dat_lich_san_bong.repository.CaRepository;
import com.example.pro2111_dat_lich_san_bong.service.CaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaServiceImpl implements CaService {

    @Autowired
    private CaRepository caRepository;

    @Override
    public List<Ca> getAll() {
        return caRepository.findAll();
    }

    @Override
    public Ca findById(String id) {
        return caRepository.findById(id).get();
    }

    @Override
    public Ca create(CaRequest caRequest) {
        Ca ca = new Ca(null,caRequest.getTenCa(), caRequest.getThoiGianBatDau(), caRequest.getThoiGianKetThuc(),
                caRequest.getGiaCa(), caRequest.getTrangThai());
        return caRepository.save(ca);
    }

    @Override
    public void remove(String id) {
        caRepository.deleteById(id);
    }

    @Override
    public Ca update(String id, CaRequest caRequest) {
        Ca ca = caRepository.findById(id).get();
        ca.setTenCa(caRequest.getTenCa());
        ca.setThoiGianBatDau(caRequest.getThoiGianBatDau());
        ca.setThoiGianKetThuc(caRequest.getThoiGianKetThuc());
        ca.setGiaCa(caRequest.getGiaCa());
        ca.setTrangThai(caRequest.getTrangThai());
        return caRepository.save(ca);
    }
}
