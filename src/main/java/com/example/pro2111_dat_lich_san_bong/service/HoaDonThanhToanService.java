package com.example.pro2111_dat_lich_san_bong.service;

import com.example.pro2111_dat_lich_san_bong.entity.HoaDonThanhToan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HoaDonThanhToanService {
    List<HoaDonThanhToan> getAllHoaDonThanhToans();

    HoaDonThanhToan getOneHoaDonThanhToan(String id);

    Boolean createNewHoaDonThanhToan(HoaDonThanhToan hoaDonThanhToan);

    Boolean updateHoaDonThanhToan(String id, HoaDonThanhToan hoaDonThanhToan);

    Boolean deleteHoaDonThanhToan(String id);
}
