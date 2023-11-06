package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoaDonUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author thepvph20110
 */
@Service
public class HoaDonUserServiceImpl implements HoaDonUserService {

    @Autowired
    private HoaDonUserRepository hoaDonUserRepository;

    @Override
    public HoaDon saveHoaDon(HoaDon hoaDon) {
        return hoaDonUserRepository.save(hoaDon);
    }

    @Override
    public List<HoaDonUserResponse> findHoaDonByChoXacNhanAndAccHT(String idAccount) {
        try {
            return hoaDonUserRepository.findHoaDonByChoXacNhanAndAccHT(idAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
