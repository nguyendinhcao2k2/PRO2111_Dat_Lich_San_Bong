package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HoaDonService {
    List<HoaDon> getAllHoaDons();

    HoaDon getOneHoaDon(String id);

    Boolean createNewHoaDon(HoaDon hoaDon);

    Boolean updateHoaDonById(String id, HoaDon hoaDon);

    Boolean deleteHoaDonById(String id);
}
