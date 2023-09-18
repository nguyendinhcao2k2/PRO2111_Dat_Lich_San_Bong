package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonThanhToan;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonThanhToanRepository;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonThanhToanServiceImpl implements HoaDonThanhToanService {
    @Autowired
    private HoaDonThanhToanRepository hoaDonThanhToanRepository;

    @Override
    public List<HoaDonThanhToan> getAllHoaDonThanhToans() {
        return hoaDonThanhToanRepository.findAll();
    }

    @Override
    public HoaDonThanhToan getOneHoaDonThanhToan(String id) {
        if (hoaDonThanhToanRepository.existsById(id)) {
            return hoaDonThanhToanRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean createNewHoaDonThanhToan(HoaDonThanhToan hoaDonThanhToan) {
        if (!hoaDonThanhToanRepository.existsById(hoaDonThanhToan.getId())) {
            hoaDonThanhToanRepository.save(hoaDonThanhToan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateHoaDonThanhToan(String id, HoaDonThanhToan hoaDonThanhToan) {
        if (hoaDonThanhToanRepository.existsById(id)) {
            hoaDonThanhToanRepository.save(hoaDonThanhToan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteHoaDonThanhToan(String id) {
        if (hoaDonThanhToanRepository.existsById(id)) {
            hoaDonThanhToanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
