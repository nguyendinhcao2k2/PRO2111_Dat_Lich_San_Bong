package com.example.pro2111_dat_lich_san_bong.core.user.service;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoaDonUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author thepvph20110
 */
public interface HoaDonUserService {

    HoaDon saveHoaDon(HoaDon hoaDon);

    List<HoaDonUserResponse> findHoaDonByChoXacNhanAndAccHT(String idAccount);

    HoaDon findHoaDonById(String idHoaDon);

    List findAllByIdAccountAndTrangThai(String idAccount,Integer trangThai);

    void updateHoaDon(HoaDon hoaDon);

    void deleteHoaDonById(String idHoaDon);
}
