package com.example.pro2111_dat_lich_san_bong.core.user.service.impl;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoaDonUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.service.HoaDonUserService;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public HoaDon findHoaDonById(String idHoaDon) {
        Optional<HoaDon> optionalHoaDon= hoaDonUserRepository.findById(idHoaDon);
        return optionalHoaDon.isPresent()?optionalHoaDon.get():new HoaDon();
    }

    @Override
    public List findAllByIdAccountAndTrangThai(String idAccount, Integer trangThai) {
        return hoaDonUserRepository.findAllByIdAccountAndTrangThai(idAccount,trangThai);
    }

    @Override
    public void updateHoaDon(HoaDon hoaDon) {
        hoaDonUserRepository.saveAndFlush(hoaDon);
    }

    @Override
    @Transactional
    public void deleteHoaDonById(String idHoaDon) {
        hoaDonUserRepository.deleteHoaDonById(idHoaDon);
    }


}
