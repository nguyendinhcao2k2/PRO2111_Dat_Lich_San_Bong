package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonSanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDonSanCa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class HoaDonSanCaUserServiceImpl implements HoaDonSanCaUserService {

    @Autowired
    private HoaDonSanCaUserRepository hoaDonSanCaUserRepository;

    @Override
    public void saveAllHoaDonSanCa(List list) {
        hoaDonSanCaUserRepository.saveAll(list);
    }

    @Override
    public void saveHoaDonSanCa(HoaDonSanCa hoaDonSanCa) {
        hoaDonSanCaUserRepository.save(hoaDonSanCa);
    }

    @Override
    public List findAllByIdHoaDon(String idHoaDon) {
        return hoaDonSanCaUserRepository.findAllByIdHoaDon(idHoaDon);
    }

    @Override
    @Transactional
    public void deleteAllByIdHoaDon(String idHoaDon) {
        hoaDonSanCaUserRepository.deleteAllByIdHoaDon(idHoaDon);
    }
}
