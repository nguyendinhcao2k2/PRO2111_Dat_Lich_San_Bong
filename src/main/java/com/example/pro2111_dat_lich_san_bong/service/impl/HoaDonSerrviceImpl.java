package com.example.pro2111_dat_lich_san_bong.service.impl;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonSerrviceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAllHoaDons() {
        return hoaDonRepository.findAll();
    }

    @Override
    public HoaDon getOneHoaDon(String id) {
        return hoaDonRepository.findById(id).get();
    }

    @Override
    public Boolean createNewHoaDon(HoaDon hoaDon) {
        if (!hoaDonRepository.existsById(hoaDon.getId())) {
            hoaDonRepository.save(hoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateHoaDonById(String id, HoaDon hoaDon) {
        if (hoaDonRepository.existsById(id)) {
            hoaDonRepository.save(hoaDon);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteHoaDonById(String id) {
        if (hoaDonRepository.existsById(id)) {
            hoaDonRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
