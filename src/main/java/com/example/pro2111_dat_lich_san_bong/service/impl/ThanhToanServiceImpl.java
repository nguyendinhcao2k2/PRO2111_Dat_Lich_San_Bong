package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.ThanhToan;
import com.example.pro2111_dat_lich_san_bong.repository.ThanhToanRepository;
import com.example.pro2111_dat_lich_san_bong.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {
    @Autowired
    private ThanhToanRepository thanhToanRepository;


    @Override
    public List<ThanhToan> getAllThanhToan() {
        return thanhToanRepository.findAll();
    }

    @Override
    public ThanhToan getOneThanhToan(String id) {
        if (thanhToanRepository.existsById(id)) {
            return thanhToanRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewThanhToan(ThanhToan thanhToan) {
        if (!thanhToanRepository.existsById(thanhToan.getId())) {
            thanhToanRepository.save(thanhToan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateThanhToan(String id, ThanhToan thanhToan) {
        if (thanhToanRepository.existsById(id)) {
            thanhToanRepository.save(thanhToan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteThanhToan(String id) {
        if (thanhToanRepository.existsById(id)) {
            thanhToanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
