package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.repository.PhieuDatLichRepository;
import com.example.pro2111_dat_lich_san_bong.service.PhieuDatLichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuDatLichServiceImpl implements PhieuDatLichService {
    @Autowired
    private PhieuDatLichRepository phieuDatLichRepository;

    @Override
    public List<PhieuDatLich> getPhieuDatLichs() {
        return phieuDatLichRepository.findAll();
    }

    @Override
    public PhieuDatLich getOnePhieuDatLich(String id) {
        if (phieuDatLichRepository.existsById(id)) {
            return phieuDatLichRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewPhieuDatLich(PhieuDatLich phieuDatLich) {
        if (!phieuDatLichRepository.existsById(phieuDatLich.getId())) {
            phieuDatLichRepository.save(phieuDatLich);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePhieuDatLich(String id, PhieuDatLich phieuDatLich) {
        if (phieuDatLichRepository.existsById(id)) {
            phieuDatLichRepository.save(phieuDatLich);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deletePhieuDatLich(String id) {
        if (phieuDatLichRepository.existsById(id)) {
            phieuDatLichRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
