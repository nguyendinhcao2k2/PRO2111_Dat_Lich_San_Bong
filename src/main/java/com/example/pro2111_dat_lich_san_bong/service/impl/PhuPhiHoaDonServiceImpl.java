package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.PhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.repository.PhuPhiHoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.service.PhuPhiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuPhiHoaDonServiceImpl implements PhuPhiHoaDonService {
    @Autowired
    private PhuPhiHoaDonRepository phuPhiHoaDonRepository;

    @Override
    public List<PhuPhiHoaDon> getAllPhuPhiHoaDons() {
        return phuPhiHoaDonRepository.findAll();
    }

    @Override
    public PhuPhiHoaDon getPhuPhiHoaDonsById(String id) {
        if (phuPhiHoaDonRepository.existsById(id)) {
            return phuPhiHoaDonRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewPhuPhiHoaDon(PhuPhiHoaDon phuPhiHoaDon) {
        if (!phuPhiHoaDonRepository.existsById(phuPhiHoaDon.getId())) {
            phuPhiHoaDonRepository.save(phuPhiHoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePhuPhiHoaDon(String id, PhuPhiHoaDon phuPhiHoaDon) {
        if (phuPhiHoaDonRepository.existsById(id)) {
            phuPhiHoaDonRepository.save(phuPhiHoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deletePhuPhiHoaDon(String id) {
        if (phuPhiHoaDonRepository.existsById(id)) {
            phuPhiHoaDonRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
